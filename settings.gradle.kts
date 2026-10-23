pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("gg.meza.stonecraft") version "1.14.5"
    id("dev.kikugie.stonecutter") version "0.9.8"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(version: String, vararg loaders: String) {
            for (it in loaders) version("$version-$it", version)
        }

        mc("1.18.2", "forge")
        mc("1.19.2", "forge")
        mc("1.20.1", "forge", "fabric")
        mc("1.20.4", "neoforge")
        mc("1.20.6", "neoforge")
        mc("1.21", "neoforge")
        mc("1.21.1", "neoforge")
        mc("26.1.2", "neoforge")
        mc("26.2", "neoforge")
        mc("26.3", "neoforge")

        vcsVersion = "26.3-neoforge"
    }
    create(rootProject)
}

rootProject.name = "AEInfinityBooster"
