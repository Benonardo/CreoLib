package com.github.creoii.creolib.mixin.client;

import com.github.creoii.creolib.api.entity.ScalableEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Camera.class)
@Environment(EnvType.CLIENT)
public class CameraMixin {
    @Shadow private Entity focusedEntity;

    @ModifyConstant(method = "update", constant = @Constant(doubleValue = 4))
    private double creo_lib_adjustFocusedCameraDistance(double constant) {
        if (focusedEntity instanceof ScalableEntity scalableEntity) {
            return constant * (scalableEntity.getScale() / 2f);
        }
        return constant;
    }
}
