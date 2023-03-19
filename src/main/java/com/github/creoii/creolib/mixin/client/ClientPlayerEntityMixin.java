package com.github.creoii.creolib.mixin.client;

import com.github.creoii.creolib.api.registry.AttributeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerAbilities;getFlySpeed()F"))
    private float creo_lib_applyFlightSpeed(PlayerAbilities instance) {
        return (float) ((ClientPlayerEntity) (Object) this).getAttributeValue(AttributeRegistry.PLAYER_FLIGHT_SPEED);
    }
}
