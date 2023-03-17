package com.github.creoii.creolib.api.world.placement;

import com.github.creoii.creolib.api.registry.PlacementModifierRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class DistanceFromZeroPlacementModifier extends AbstractConditionalPlacementModifier {
    public static final Codec<DistanceFromZeroPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.INT.fieldOf("threshold").forGetter(predicate -> {
            return predicate.threshold;
        })).apply(instance, DistanceFromZeroPlacementModifier::new);
    });
    private final int threshold;

    public DistanceFromZeroPlacementModifier(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        return context.getWorld().getChunk(pos).getPos().getStartPos().getSquaredDistance(Vec3d.ZERO) >= threshold;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return PlacementModifierRegistry.DISTANCE_FROM_ZERO;
    }
}
