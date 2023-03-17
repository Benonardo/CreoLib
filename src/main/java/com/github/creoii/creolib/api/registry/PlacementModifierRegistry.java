package com.github.creoii.creolib.api.registry;

import com.github.creoii.creolib.api.world.placement.*;
import com.github.creoii.creolib.core.CreoLib;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public final class PlacementModifierRegistry {
    public static final PlacementModifierType<NoisePlacementModifier> NOISE = () -> NoisePlacementModifier.CODEC;
    public static final PlacementModifierType<FastNoisePlacementModifier> FAST_NOISE = () -> FastNoisePlacementModifier.CODEC;
    public static final PlacementModifierType<SteepPlacementModifier> STEEP = () -> SteepPlacementModifier.CODEC;
    public static final PlacementModifierType<DimensionPlacementModifier> DIMENSION = () -> DimensionPlacementModifier.CODEC;
    public static final PlacementModifierType<OffsetPlacementModifier> OFFSET = () -> OffsetPlacementModifier.CODEC;
    public static final PlacementModifierType<DensityFunctionPlacementModifier> DENSITY_FUNCTION = () -> DensityFunctionPlacementModifier.CODEC;
    public static final PlacementModifierType<NearStructurePlacementModifier> NEAR_STRUCTURE = () -> NearStructurePlacementModifier.CODEC;
    public static final PlacementModifierType<SkyVisiblePlacementModifier> SKY_VISIBLE = () -> SkyVisiblePlacementModifier.CODEC;
    public static final PlacementModifierType<DistanceFromZeroPlacementModifier> DISTANCE_FROM_ZERO = () -> DistanceFromZeroPlacementModifier.CODEC;

    public static void register() {
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "noise"), NOISE);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "fast_noise"), FAST_NOISE);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "steep"), STEEP);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "dimension"), DIMENSION);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "offset"), OFFSET);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "density_function"), DENSITY_FUNCTION);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "near_structure"), NEAR_STRUCTURE);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "sky_visible"), SKY_VISIBLE);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(CreoLib.NAMESPACE, "distance_from_zero"), DISTANCE_FROM_ZERO);
    }
}
