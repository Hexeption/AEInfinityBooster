import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

val minecraft = stonecutter.current.version
val unobfuscated = stonecutter.eval(minecraft, ">=26.1")

// 1.16.5 players are on Java 8, and the game test framework only arrived in 1.17
if (stonecutter.eval(minecraft, "<1.17")) {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        toolchain.languageVersion = JavaLanguageVersion.of(8)
    }
    loom.runs.named("gameTestServer") {
        server()
    }

    // Dev runs only; see MixinTransformation
    val dev = sourceSets.create("dev") {
        compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().runtimeClasspath + sourceSets.main.get().output
    }
    loom.runs.configureEach {
        source(dev)
        programArgs("--mixin.config", "aeinfinitybooster-dev.mixins.json")
    }
    tasks.named("runGameTestServer") {
        enabled = false
    }
}

// AE2 renamed the access point classes in 1.20
stonecutter {
    replacements.regex(stonecutter.eval(minecraft, "<1.20")) {
        replace(
            "\\bWirelessAccessPoint(BlockEntity|Menu|Screen)\\b", "Wireless\$1",
            "\\bWireless(BlockEntity|Menu|Screen)\\b", "WirelessAccessPoint\$1",
        )
    }
    // ItemStack.is arrived in 1.17
    replacements.regex(stonecutter.eval(minecraft, "<1.17")) {
        replace(
            "\\.is\\((ModItems\\.\\w+\\.get\\(\\))\\)", ".getItem() == \$1",
            "\\.getItem\\(\\) == (ModItems\\.\\w+\\.get\\(\\))", ".is(\$1)",
        )
    }
}

repositories {
    mavenCentral()
    maven("https://modmaven.dev/") { name = "ModMaven" }
    maven("https://maven.blamejared.com/") { name = "BlameJared" }
    maven("https://maven.shedaniel.me/") { name = "Shedaniel" }
    maven("https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") { name = "DevAuth" }
}

// AE2's maven group and artifact name differ between Minecraft versions
val ae2 = mod.prop("ae2_version").let { version ->
    when {
        stonecutter.eval(minecraft, "<1.17") -> "appeng:appliedenergistics2:$version"
        mod.isForge -> "appeng:appliedenergistics2-forge:$version"
        mod.isFabric -> "appeng:appliedenergistics2-fabric:$version"
        stonecutter.eval(minecraft, "<1.21") -> "appeng:appliedenergistics2-neoforge:$version"
        stonecutter.eval(minecraft, "<1.21.1") -> "appeng:appliedenergistics2:$version"
        else -> "org.appliedenergistics:appliedenergistics2:$version"
    }
}

dependencies {
    val compile = if (unobfuscated) "compileOnly" else "modCompileOnly"
    val runtime = if (unobfuscated) "localRuntime" else "modLocalRuntime"

    add(compile, ae2)
    add(runtime, ae2)

    if (mod.isFabric) {
        // Cloth Config backs the config on Fabric; ModMenu is optional and only adds the in-game config button
        add("modApi", "me.shedaniel.cloth:cloth-config-fabric:${mod.prop("cloth_config_version")}") {
            exclude(group = "net.fabricmc.fabric-api")
        }
        add("modCompileOnly", "com.terraformersmc:modmenu:${mod.prop("modmenu_version")}")
        add("modLocalRuntime", "com.terraformersmc:modmenu:${mod.prop("modmenu_version")}")
        // AE2 jar-in-jars Team Reborn Energy, but dev runs don't pick the nested copy up
        add("modLocalRuntime", "teamreborn:energy:${mod.prop("tr_energy_version")}")
    }

    // Lets runClient sign in with a real Microsoft account. Dev runtime only, so it isn't in the built jar
    add(runtime, "me.djtheredstoner:DevAuth-${if (mod.isForge) "forge-latest" else mod.loader}:${property("devauth_version")}")

    if (mod.hasProp("guideme_version")) {
        add(runtime, "org.appliedenergistics:guideme:${mod.prop("guideme_version")}")
    }

    if (mod.hasProp("jei_version")) {
        add(runtime, "mezz.jei:jei-$minecraft-${mod.loader}:${mod.prop("jei_version")}") {
            // Loom remaps these annotation jars as mods, and Forge 1.20.1 crashes reading their multi-release layout
            exclude(group = "org.jetbrains", module = "annotations")
            exclude(group = "org.jspecify")
        }
    }
}

// Fabric has no datagen here, so it reuses the Forge 1.20.1 recipes and models
if (mod.isFabric) {
    sourceSets.main {
        resources.srcDir(rootProject.file("versions/$minecraft-forge/src/main/generated"))
    }
    tasks.named("runDatagen") {
        enabled = false
    }
}

// Forge 1.20.1 treats every classpath folder as its own mod and crashes on the bare generated folder Stonecraft adds,
// so fold the generated files into the normal resources instead
if (mod.isForge) {
    val generated = file("src/main/generated")
    sourceSets.main {
        resources.srcDir(generated)
    }
    afterEvaluate {
        tasks.withType<JavaExec>().configureEach {
            classpath = classpath.filter { it != generated }
        }
    }
}

// DevAuth supplies the real account name, and Minecraft refuses a second --username from Stonecraft's defaults
afterEvaluate {
    loom.runs.named("client") {
        programArguments.set(programArguments.get().filterNot { it.startsWith("--username") })
    }
}

tasks.named<Jar>("jar") {
    // Datagen bookkeeping stays in git but not in the jar. 26.3 also writes the same recipes to client/ and server/
    exclude(".cache/**")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

loom {
    runs.named("client") {
        jvmArguments.add("-Ddevauth.enabled=true")
    }
    if (mod.isForge) {
        forge {
            mixinConfig("${mod.id}.mixins.json")
        }
        // Without this Forge loads the classes and resources folders as two separate mods
        mods {
            register(mod.id) {
                sourceSet(sourceSets.main.get())
                sourceSets.findByName("dev")?.let { sourceSet(it) }
            }
        }
    }
}

modSettings {
    runDirectory = rootProject.layout.projectDirectory.dir("run/${project.name}")
    variableReplacements = mapOf(
        "authors" to project.property("mod.authors").toString(),
        "license" to project.property("mod.license").toString(),
        "ae2VersionRange" to mod.prop("ae2_version_range"),
        // e.g. 21.1.264 -> [21.1,) so players aren't forced onto the exact NeoForge build we compiled against
        "neoforgeVersionRange" to "[${mod.prop("neoforge_version", "0.0").substringBeforeLast('.')},)",
        "forgeLoaderVersion" to mod.prop("forge_loader_version", ""),
        "mixinCompatibility" to if (stonecutter.eval(minecraft, "<1.17")) "JAVA_8" else "JAVA_17",
    )
}

publishMods {
    modrinth {
        requires("ae2")
        if (mod.isFabric) requires("fabric-api")
    }
    curseforge {
        client = true
        server = true
        requires("applied-energistics-2")
        if (mod.isFabric) requires("fabric-api")
    }
}
