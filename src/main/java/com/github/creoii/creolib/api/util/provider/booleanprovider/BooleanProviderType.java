package com.github.creoii.creolib.api.util.provider.booleanprovider;

import com.github.creoii.creolib.api.registry.CRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registry;

public interface BooleanProviderType<P extends BooleanProvider> {
    Codec<P> codec();

    static <P extends BooleanProvider> BooleanProviderType<P> register(String id, Codec<P> codec) {
        return Registry.register(CRegistries.BOOLEAN_PROVIDER_TYPE, id, () -> codec);
    }
}
