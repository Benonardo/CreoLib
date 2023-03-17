package com.github.creoii.creolib.core;

import com.github.creoii.creolib.api.tag.CBiomeTags;
import com.github.creoii.creolib.core.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public final class CreoLib implements ModInitializer {
    public static final String NAMESPACE = "creo";
    public static final String COMMON = "c";

    @Override
    public void onInitialize() {
        AttributeRegistry.register();
        FeatureRegistry.register();
        PlacementModifierRegistry.register();
        StructurePlacementTypeRegistry.register();
        MaterialConditionRegistry.register();
        DensityFunctionTypeRegistry.register();
        CBiomeTags.loadVanillaFeatureTags();
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(NAMESPACE, "test_paint")));
    }
}
