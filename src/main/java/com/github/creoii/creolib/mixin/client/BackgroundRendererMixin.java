package com.github.creoii.creolib.mixin.client;

import com.github.creoii.creolib.api.util.BiomeFogModifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private static void creo_lib_applyBiomeFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, CameraSubmersionType cameraSubmersionType, Entity entity, BackgroundRenderer.FogData fogData) {
        RegistryEntry<Biome> registryEntry = entity.world.getBiome(entity.getBlockPos());
        if (registryEntry.getKey().isPresent()) {
            RegistryKey<Biome> biomeKey = registryEntry.getKey().get();
            if (BiomeFogModifier.getBiomeFogModifiers().containsKey(biomeKey)) {
                BiomeFogModifier modifier = BiomeFogModifier.getBiomeFogModifiers().get(biomeKey);
                fogData.fogStart = modifier.fogStart().apply(new BiomeFogModifier.FogFunction(camera, (ClientWorld) entity.world, entity, viewDistance, tickDelta));
                fogData.fogEnd = modifier.fogEnd().apply(new BiomeFogModifier.FogFunction(camera, (ClientWorld) entity.world, entity, viewDistance, tickDelta));
                fogData.fogShape = modifier.fogShape();
                RenderSystem.setShaderFogStart(fogData.fogStart);
                RenderSystem.setShaderFogEnd(fogData.fogEnd);
                RenderSystem.setShaderFogShape(fogData.fogShape);
                ci.cancel();
            }
        }
    }
}
