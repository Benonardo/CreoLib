package com.github.creoii.creolib.api.util.provider.floatprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.floatprovider.FloatProviderType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WorldFloatProvider extends FloatProvider {
    public static final Codec<WorldFloatProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, WorldFloatProvider::new);
    });
    private final String key;
    private World world;

    public WorldFloatProvider(String key) {
        this.key = key;
    }

    public WorldFloatProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    @Override
    public float get(Random random) {
        return switch (key) {
            case "rain_gradient" -> world.getRainGradient(1f);
            case "sky_angle" -> world.getSkyAngle(1f);
            case "sky_angle_radians" -> world.getSkyAngleRadians(1f);
            case "spawn_angle" -> world.getSpawnAngle();
            case "thunder_gradient" -> world.getThunderGradient(1f);
            case "moon_size" -> world.getMoonSize();
            default -> 1f;
        };
    }

    @Override
    public float getMin() {
        return Float.MIN_VALUE;
    }

    @Override
    public float getMax() {
        return Float.MAX_VALUE;
    }

    @Override
    public FloatProviderType<?> getType() {
        return null;
    }
}
