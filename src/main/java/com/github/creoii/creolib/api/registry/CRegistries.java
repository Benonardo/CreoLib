package com.github.creoii.creolib.api.registry;

import com.github.creoii.creolib.api.util.provider.booleanprovider.BooleanProviderType;
import com.github.creoii.creolib.core.CreoLib;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CRegistries {
    public static final Registry<BooleanProviderType<?>> BOOLEAN_PROVIDER_TYPE = FabricRegistryBuilder.createDefaulted(CRegistryKeys.BOOLEAN_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "constant")).buildAndRegister();
}
