package com.github.creoii.creolib.mixin.entity;

import com.github.creoii.creolib.api.util.registry.CItemSettings;
import com.github.creoii.creolib.core.duck.ItemSettingsDuck;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Shadow private int pickupDelay;
    private int despawnTime;

    @Inject(method = "<init>(Lnet/minecraft/entity/ItemEntity;)V", at = @At("TAIL"))
    private void creo_lib_applyDespawnTimes(ItemEntity entity, CallbackInfo ci) {
        if (((ItemSettingsDuck) entity.getStack().getItem()).getItemSettings() instanceof CItemSettings settings) {
            despawnTime = settings.getDespawnTime();
            pickupDelay = despawnTime;
        }
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 6000))
    private int creo_lib_tickDespawnTime(int constant) {
        return despawnTime;
    }
}
