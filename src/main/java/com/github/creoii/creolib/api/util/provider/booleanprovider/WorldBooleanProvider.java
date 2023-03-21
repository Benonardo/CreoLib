package com.github.creoii.creolib.api.util.provider.booleanprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.IntProviderType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WorldBooleanProvider extends BooleanProvider {
    public static final Codec<WorldBooleanProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, WorldBooleanProvider::new);
    });
    private final String key;
    private World world;

    public WorldBooleanProvider(String key) {
        this.key = key;
    }

    public WorldBooleanProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    @Override
    public boolean get(Random random) {
        return switch (key) {
            case "is_client" -> world.isClient;
            case "is_day" -> world.isDay();
            case "is_debug_world" -> world.isDebugWorld();
            case "is_night" -> world.isNight();
            case "is_raining" -> world.isRaining();
            case "is_thundering" -> world.isThundering();
        };
    }

    @Override
    public BooleanProviderType<?> getType() {
        return ProvidersRegistry.WORLD_BOOLEAN;
    }
}
