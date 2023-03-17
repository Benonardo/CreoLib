package com.github.creoii.creolib.api.world.placement;

import com.github.creoii.creolib.api.registry.PlacementModifierRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class SkyVisiblePlacementModifier extends AbstractConditionalPlacementModifier {
    public static final Codec<SkyVisiblePlacementModifier> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("ignored").forGetter(placement -> {
            return placement.ignored;
        })).apply(instance, SkyVisiblePlacementModifier::new);
    });
    private final RegistryEntryList<Block> ignored;

    public SkyVisiblePlacementModifier(RegistryEntryList<Block> ignored) {
        this.ignored = ignored;
    }

    @Override
    public boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        StructureWorldAccess world = context.getWorld();
        for (BlockPos pos1 : BlockPos.iterate(pos.up(), new BlockPos(pos.getX(), world.getHeight(), pos.getZ()))) {
            if (!world.isAir(pos1) || !world.getBlockState(pos1).isIn(ignored)) return false;
        }
        return true;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return PlacementModifierRegistry.SKY_VISIBLE;
    }
}
