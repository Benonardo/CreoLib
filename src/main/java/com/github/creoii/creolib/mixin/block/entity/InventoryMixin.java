package com.github.creoii.creolib.mixin.block.entity;

import com.github.creoii.creolib.api.registry.AttributeRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public interface InventoryMixin {
    @Inject(method = "canPlayerUse(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void creo_lib_reachAccessLootableContainers(BlockEntity blockEntity, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(Inventory.canPlayerUse(blockEntity, player, (int) player.getAttributeValue(AttributeRegistry.PLAYER_REACH_DISTANCE)));
    }
}
