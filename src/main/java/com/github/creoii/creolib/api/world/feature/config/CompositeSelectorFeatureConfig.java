package com.github.creoii.creolib.api.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

public record CompositeSelectorFeatureConfig(List<List<RegistryEntry<PlacedFeature>>> features, boolean failIfFirstFails) implements FeatureConfig {
    public static final Codec<CompositeSelectorFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(PlacedFeature.REGISTRY_CODEC.listOf().listOf().fieldOf("features").forGetter(config -> {
            return config.features;
        }), Codec.BOOL.fieldOf("fail_if_first_fails").orElse(true).forGetter(config -> {
            return config.failIfFirstFails;
        })).apply(instance, CompositeSelectorFeatureConfig::new);
    });
}
