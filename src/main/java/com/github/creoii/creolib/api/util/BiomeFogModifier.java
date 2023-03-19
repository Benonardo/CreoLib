package com.github.creoii.creolib.api.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Used to give a biome custom fog densities
 *
 * @param fogStart distance from the camera where the fog starts
 * @param fogEnd distance from the camera where the fog is completely dense
 */
@Environment(EnvType.CLIENT)
public record BiomeFogModifier(Function<FogFunction, Float> fogStart, Function<FogFunction, Float> fogEnd, FogShape fogShape) {
    private static final Map<RegistryKey<Biome>, BiomeFogModifier> BIOME_FOG_MODIFIERS = new HashMap<>();

    public record FogFunction(Camera camera, ClientWorld world, Entity focusedEntity, float viewDistance, float tickDelta) { }

    public static void register(RegistryKey<Biome> biomeKey, BiomeFogModifier modifier) {
        BIOME_FOG_MODIFIERS.put(biomeKey, modifier);
    }

    public static Map<RegistryKey<Biome>, BiomeFogModifier> getBiomeFogModifiers() {
        return BIOME_FOG_MODIFIERS;
    }
}