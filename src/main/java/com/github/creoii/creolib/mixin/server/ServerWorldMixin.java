package com.github.creoii.creolib.mixin.server;

import com.github.creoii.creolib.api.tag.CBlockTags;
import com.github.creoii.creolib.api.util.ticking.Ticker;
import net.fabricmc.api.EnvType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/RaidManager;tick()V", shift = At.Shift.AFTER))
    private void creo_lib_applyServerWorldTickers(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Ticker.TICKERS.forEach(ticker -> {
            if (ticker.environment() != Ticker.Environment.CLIENT) {
                ticker.tickable().tick((ServerWorld) (Object) this);
            }
        });
    }

    @Redirect(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private boolean creo_lib_lightningAttractors(BlockState instance, Block block) {
        return instance.isIn(CBlockTags.ATTRACTS_LIGHTNING);
    }
}
