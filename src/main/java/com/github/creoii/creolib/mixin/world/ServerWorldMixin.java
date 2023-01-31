package com.github.creoii.creolib.mixin.world;

import com.github.creoii.creolib.util.Tickable;
import com.github.creoii.creolib.world.ServerWorldTicker;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/RaidManager;tick()V", shift = At.Shift.AFTER))
    private void creo_lib_applyServerWorldTickers(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerWorldTicker.TICKERS.forEach(ticker -> {
            ticker.tick((ServerWorld) (Object) this);
        });
        Tickable.TICKERS.forEach(tickable -> {
            tickable.tick((World) (Object) this);
        });
    }
}