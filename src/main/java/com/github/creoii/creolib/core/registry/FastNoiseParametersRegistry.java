package com.github.creoii.creolib.core.registry;

import com.github.creoii.creolib.core.CreoLib;
import com.github.creoii.creolib.core.noise.FastNoiseLite;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public final class FastNoiseParametersRegistry extends FabricDynamicRegistryProvider {
    public static final RegistryKey<Registry<FastNoiseLite>> FAST_NOISE_SETTINGS = RegistryKey.ofRegistry(new Identifier(CreoLib.NAMESPACE, "worldgen/fast_noise"));
    public static final Codec<RegistryEntry<FastNoiseLite>> REGISTRY_CODEC = RegistryElementCodec.of(FAST_NOISE_SETTINGS, FastNoiseLite.CODEC);

    public FastNoiseParametersRegistry(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.add(RegistryKey.of(FAST_NOISE_SETTINGS, new Identifier(CreoLib.NAMESPACE, "noodle")), new FastNoiseLite()
                .setNoiseType(FastNoiseLite.NoiseType.OPEN_SIMPLEX_2_S)
                .setFractalType(FastNoiseLite.FractalType.PING_PONG)
                .setFractalGain(0f)
                .setFractalPingPongStrength(4f)
        );
        entries.add(RegistryKey.of(FAST_NOISE_SETTINGS, new Identifier(CreoLib.NAMESPACE, "honeycomb")), new FastNoiseLite()
                .setNoiseType(FastNoiseLite.NoiseType.CELLULAR)
                .setFrequency(.015f)
                .setFractalType(FastNoiseLite.FractalType.RIDGED)
                .setFractalLacunarity(0f)
        );
        entries.add(RegistryKey.of(FAST_NOISE_SETTINGS, new Identifier(CreoLib.NAMESPACE, "ridged_gridlike")), new FastNoiseLite()
                .setNoiseType(FastNoiseLite.NoiseType.VALUE)
                .setFrequency(.02f)
                .setFractalType(FastNoiseLite.FractalType.RIDGED)
                .setFractalLacunarity(0f)
        );
        entries.add(RegistryKey.of(FAST_NOISE_SETTINGS, new Identifier(CreoLib.NAMESPACE, "constant_zones")), new FastNoiseLite()
                .setNoiseType(FastNoiseLite.NoiseType.CELLULAR)
                .setFrequency(.02f)
                .setFractalType(FastNoiseLite.FractalType.NONE)
                .setCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EUCLIDEAN)
                .setCellularReturnType(FastNoiseLite.CellularReturnType.CELL_VALUE)
        );
        entries.add(RegistryKey.of(FAST_NOISE_SETTINGS, new Identifier(CreoLib.NAMESPACE, "grid")), new FastNoiseLite()
                .setNoiseType(FastNoiseLite.NoiseType.CELLULAR)
                .setFrequency(.025f)
                .setFractalType(FastNoiseLite.FractalType.NONE)
                .setCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EUCLIDEAN)
                .setCellularReturnType(FastNoiseLite.CellularReturnType.CELL_VALUE)
                .setCellularJitter(0f)
        );
        entries.add(RegistryKey.of(FAST_NOISE_SETTINGS, new Identifier(CreoLib.NAMESPACE, "web")), new FastNoiseLite()
                .setNoiseType(FastNoiseLite.NoiseType.CELLULAR)
                .setFrequency(.015f)
                .setFractalType(FastNoiseLite.FractalType.NONE)
                .setCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EUCLIDEAN)
                .setCellularReturnType(FastNoiseLite.CellularReturnType.DISTANCE)
                .setCellularJitter(1.5f));
    }

    @Override
    public String getName() {
        return "FastNoise Parameters";
    }
}
