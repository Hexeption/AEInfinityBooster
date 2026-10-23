package uk.co.hexeption.aeinfinitybooster.dev.mixins;

import com.mojang.math.Transformation;
import net.minecraftforge.client.extensions.IForgeTransformationMatrix;
import org.spongepowered.asm.mixin.Mixin;

// Loom leaves BlockMath calling these by their SRG names on 1.16.5, which crashes the dev client while baking models
@Mixin(value = Transformation.class, remap = false)
public abstract class MixinTransformation implements IForgeTransformationMatrix {

    public Transformation func_227987_b_() {
        return inverse();
    }

    public Transformation func_227985_a_(Transformation other) {
        return compose(other);
    }
}
