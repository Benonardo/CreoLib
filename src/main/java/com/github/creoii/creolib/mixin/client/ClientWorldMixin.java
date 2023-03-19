package com.github.creoii.creolib.mixin.client;

import com.github.creoii.creolib.api.util.ticking.Ticker;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Shadow @Final private ClientWorld.Properties clientWorldProperties;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;tickTime()V", shift = At.Shift.AFTER))
    private void creo_lib_applyServerWorldTickers(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Ticker.TICKERS.forEach(ticker -> {
            if (ticker.environment() != Ticker.Environment.SERVER) {
                if (clientWorldProperties.getTime() % ticker.interval() == 0)
                    ticker.tickable().tick((ClientWorld) (Object) this);
            }
        });
    }
}
