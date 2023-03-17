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
    private static final Random RANDOM = Random.create();
    public static final Codec<DistanceFromZeroPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.INT.fieldOf("min_squared_distance").forGetter(predicate -> {
            return predicate.minSquaredDistance;
        })).apply(instance, DistanceFromZeroPlacementModifier::new);
    });
    private final int minSquaredDistance;

    public DistanceFromZeroPlacementModifier(int minSquaredDistance) {
        this.minSquaredDistance = minSquaredDistance;
    }

    @Override
    public boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        return new Vec3d(pos.getX(), 0, pos.getZ()).squaredDistanceTo(Vec3d.ZERO) >= minSquaredDistance;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return PlacementModifierRegistry.DISTANCE_FROM_ZERO;
    }
}
