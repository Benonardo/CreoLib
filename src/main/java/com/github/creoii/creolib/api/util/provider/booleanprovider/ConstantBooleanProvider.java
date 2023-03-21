package com.github.creoii.creolib.api.util.provider.booleanprovider;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.random.Random;

public class ConstantBooleanProvider extends BooleanProvider {
    private static final Codec<ConstantBooleanProvider> BASE_CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.BOOL.fieldOf("value").forGetter(provider -> {
            return provider.value;
        })).apply(instance, ConstantBooleanProvider::new);
    });
    public static final Codec<ConstantBooleanProvider> CODEC = Codec.either(Codec.BOOL, BASE_CODEC).xmap(either -> either.map(ConstantBooleanProvider::create, provider -> provider), provider -> Either.left(provider.value));
    private final boolean value;

    public static ConstantBooleanProvider create(boolean value) {
        return new ConstantBooleanProvider(value);
    }

    private ConstantBooleanProvider(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean get(Random random) {
        return value;
    }

    @Override
    public BooleanProviderType<?> getType() {
        return BooleanProviderType.CONSTANT;
    }

    public String toString() {
        return Boolean.toString(value);
    }
}
