package com.github.creoii.creolib.api.registry;

import com.github.creoii.creolib.api.util.provider.booleanprovider.BooleanProviderType;
import com.github.creoii.creolib.core.CreoLib;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class CRegistryKeys {
    public static final RegistryKey<Registry<BooleanProviderType<?>>> BOOLEAN_PROVIDER_TYPE = RegistryKey.ofRegistry(new Identifier(CreoLib.NAMESPACE, "boolean_provider_type"));
}
