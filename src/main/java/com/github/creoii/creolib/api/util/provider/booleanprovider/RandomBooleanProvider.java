package com.github.creoii.creolib.api.util.provider.booleanprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.random.Random;

public class RandomBooleanProvider extends BooleanProvider {
    private static final RandomBooleanProvider INSTANCE = new RandomBooleanProvider();
    public static final Codec<RandomBooleanProvider> CODEC = Codec.unit(() -> INSTANCE);

    public static RandomBooleanProvider create() {
        return new RandomBooleanProvider();
    }

    private RandomBooleanProvider() {}

    @Override
    public boolean get(Random random) {
        return random.nextBoolean();
    }

    @Override
    public BooleanProviderType<?> getType() {
        return ProvidersRegistry.RANDOM_BOOLEAN;
    }
}
