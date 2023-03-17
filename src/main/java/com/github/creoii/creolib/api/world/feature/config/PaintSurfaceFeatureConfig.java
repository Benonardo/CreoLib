package com.github.creoii.creolib.api.world.feature.config;

import com.github.creoii.creolib.core.noise.FastNoiseLite;
import com.github.creoii.creolib.api.registry.FastNoiseParametersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

/**
 * @param noiseEntry noise function to use, defined in data/namespace/worldgen/fast_noise
 * @param state stateProvider to pick blockstates from
 * @param threshold noise value threshold; any value <= threshold results in a block placed
 * @param yOffset optional amount to offset y value; defaults to 0
 */
public record PaintSurfaceFeatureConfig(RegistryEntry<FastNoiseLite> noiseEntry, BlockStateProvider state, float threshold, int yOffset) implements FeatureConfig {
    public static final Codec<PaintSurfaceFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(FastNoiseParametersRegistry.REGISTRY_CODEC.fieldOf("noise").forGetter(predicate -> {
            return predicate.noiseEntry;
        }), BlockStateProvider.TYPE_CODEC.fieldOf("state").forGetter(config -> {
            return config.state;
        }), Codec.FLOAT.fieldOf("threshold").forGetter(config -> {
            return config.threshold;
        }), Codec.INT.fieldOf("y_offset").orElse(0).forGetter(config -> {
            return config.yOffset;
        })).apply(instance, PaintSurfaceFeatureConfig::new);
    });
}