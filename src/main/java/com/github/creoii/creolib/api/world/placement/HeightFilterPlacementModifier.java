package com.github.creoii.creolib.api.world.placement;

import com.github.creoii.creolib.api.registry.PlacementModifierRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class HeightFilterPlacementModifier extends AbstractConditionalPlacementModifier {
    public static final Codec<HeightFilterPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Heightmap.Type.CODEC.fieldOf("heightmap").forGetter(predicate -> {
            return predicate.heightmap;
        }), HeightProvider.CODEC.fieldOf("lower_height").forGetter(predicate -> {
            return predicate.lowerHeight;
        }), HeightProvider.CODEC.fieldOf("upper_height").forGetter(predicate -> {
            return predicate.upperHeight;
        })).apply(instance, HeightFilterPlacementModifier::new);
    });
    private final Heightmap.Type heightmap;
    private final HeightProvider lowerHeight;
    private final HeightProvider upperHeight;

    public HeightFilterPlacementModifier(Heightmap.Type heightmap, HeightProvider lowerHeight, HeightProvider upperHeight) {
        this.heightmap = heightmap;
        this.lowerHeight = lowerHeight;
        this.upperHeight = upperHeight;
    }

    @Override
    public boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        int topY = context.getTopY(heightmap, pos.getX(), pos.getZ());
        return topY >= lowerHeight.get(random, context) && topY <= upperHeight.get(random, context);
    }

    @Override
    public PlacementModifierType<?> getType() {
        return PlacementModifierRegistry.HEIGHT_FILTER;
    }
}
