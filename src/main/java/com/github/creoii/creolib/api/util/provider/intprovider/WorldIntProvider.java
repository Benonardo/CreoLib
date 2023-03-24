package com.github.creoii.creolib.api.util.provider.intprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.IntProviderType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WorldIntProvider extends IntProvider {
    public static final Codec<WorldIntProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, WorldIntProvider::new);
    });
    private final String key;
    private World world;

    public WorldIntProvider(String key) {
        this.key = key;
    }

    public WorldIntProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    @Override
    public int get(Random random) {
        return switch (key) {
            case "time" -> (int) world.getTime();
            case "ambient_darkness" -> world.getAmbientDarkness();
            case "sea_level" -> world.getSeaLevel();
            case "bottom_y" -> world.getBottomY();
            case "height" -> world.getHeight();
            case "top_y" -> world.getTopY();
            case "time_of_day" -> (int) world.getTimeOfDay();
            case "lunar_time" -> (int) world.getLunarTime();
            case "moon_phase" -> world.getMoonPhase();
            default -> -1;
        };
    }

    @Override
    public int getMin() {
        return Integer.MIN_VALUE;
    }

    @Override
    public int getMax() {
        return Integer.MAX_VALUE;
    }

    @Override
    public IntProviderType<?> getType() {
        return ProvidersRegistry.WORLD_INT;
    }
}
