package com.github.creoii.creolib.api.registry;

import com.github.creoii.creolib.api.util.provider.booleanprovider.*;
import com.github.creoii.creolib.api.util.provider.floatprovider.BlockStateFloatProvider;
import com.github.creoii.creolib.api.util.provider.floatprovider.EntityFloatProvider;
import com.github.creoii.creolib.api.util.provider.floatprovider.WorldFloatProvider;
import com.github.creoii.creolib.api.util.provider.intprovider.*;
import com.github.creoii.creolib.core.CreoLib;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.floatprovider.FloatProviderType;
import net.minecraft.util.math.intprovider.IntProviderType;

public class ProvidersRegistry {
    public static final IntProviderType<WorldIntProvider> WORLD_INT = () -> WorldIntProvider.CODEC;
    public static final IntProviderType<EntityIntProvider> ENTITY_INT = () -> EntityIntProvider.CODEC;
    public static final IntProviderType<BlockStateIntProvider> BLOCKSTATE_INT = () -> BlockStateIntProvider.CODEC;
    public static final FloatProviderType<WorldFloatProvider> WORLD_FLOAT = () -> WorldFloatProvider.CODEC;
    public static final FloatProviderType<EntityFloatProvider> ENTITY_FLOAT = () -> EntityFloatProvider.CODEC;
    public static final FloatProviderType<BlockStateFloatProvider> BLOCKSTATE_FLOAT = () -> BlockStateFloatProvider.CODEC;
    public static final BooleanProviderType<ConstantBooleanProvider> CONSTANT_BOOLEAN = () -> ConstantBooleanProvider.CODEC;
    public static final BooleanProviderType<RandomBooleanProvider> RANDOM_BOOLEAN = () -> RandomBooleanProvider.CODEC;
    public static final BooleanProviderType<WorldBooleanProvider> WORLD_BOOLEAN = () -> WorldBooleanProvider.CODEC;
    public static final BooleanProviderType<EntityBooleanProvider> ENTITY_BOOLEAN = () -> EntityBooleanProvider.CODEC;

    public static void register() {
        Registry.register(Registries.INT_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "world"), WORLD_INT);
        Registry.register(Registries.INT_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "entity"), ENTITY_INT);
        Registry.register(Registries.INT_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "blockstate"), BLOCKSTATE_INT);
        Registry.register(Registries.FLOAT_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "world"), WORLD_FLOAT);
        Registry.register(Registries.FLOAT_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "entity"), ENTITY_FLOAT);
        Registry.register(Registries.FLOAT_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "blockstate"), BLOCKSTATE_FLOAT);
        Registry.register(CRegistries.BOOLEAN_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "constant"), CONSTANT_BOOLEAN);
        Registry.register(CRegistries.BOOLEAN_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "random"), RANDOM_BOOLEAN);
        Registry.register(CRegistries.BOOLEAN_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "world"), WORLD_BOOLEAN);
        Registry.register(CRegistries.BOOLEAN_PROVIDER_TYPE, new Identifier(CreoLib.NAMESPACE, "entity"), ENTITY_BOOLEAN);
    }
}
