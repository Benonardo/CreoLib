package com.github.creoii.creolib.api.registry;

import com.github.creoii.creolib.api.world.feature.*;
import com.github.creoii.creolib.api.world.feature.config.*;
import com.github.creoii.creolib.core.CreoLib;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;

public final class FeatureRegistry {
    public static final Feature<CompositeFeatureConfig> COMPOSITE = new CompositeFeature(CompositeFeatureConfig.CODEC);
    public static final Feature<CompositeSelectorFeatureConfig> COMPOSITE_SELECTOR = new CompositeSelectorFeature(CompositeSelectorFeatureConfig.CODEC);
    public static final Feature<DeformedCircleFeatureConfig> DEFORMED_CIRCLE = new DeformedCircleFeature(DeformedCircleFeatureConfig.CODEC);
    public static final Feature<PaintSurfaceFeatureConfig> PAINT_SURFACE = new PaintSurfaceFeature(PaintSurfaceFeatureConfig.CODEC);
    public static final Feature<PoolFeatureConfig> POOL = new PoolFeature(PoolFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE, new Identifier(CreoLib.NAMESPACE, "composite"), COMPOSITE);
        Registry.register(Registries.FEATURE, new Identifier(CreoLib.NAMESPACE, "composite_selector"), COMPOSITE_SELECTOR);
        Registry.register(Registries.FEATURE, new Identifier(CreoLib.NAMESPACE, "deformed_circle"), DEFORMED_CIRCLE);
        Registry.register(Registries.FEATURE, new Identifier(CreoLib.NAMESPACE, "paint_surface"), PAINT_SURFACE);
        Registry.register(Registries.FEATURE, new Identifier(CreoLib.NAMESPACE, "pool"), POOL);
    }
}
