package com.github.creoii.creolib.api.world.feature;

import com.github.creoii.creolib.api.world.feature.config.PaintSurfaceFeatureConfig;
import com.github.creoii.creolib.core.noise.FastNoiseLite;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PaintSurfaceFeature extends Feature<PaintSurfaceFeatureConfig> {
    public PaintSurfaceFeature(Codec<PaintSurfaceFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<PaintSurfaceFeatureConfig> context) {
        Random random = context.getRandom();
        PaintSurfaceFeatureConfig config = context.getConfig();

        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        FastNoiseLite noise = config.noiseEntry().value();
        int originX = (origin.getX() & ~15);
        int originZ = (origin.getZ() & ~15);
        for (int x = originX; x < originX + 16; ++x) {
            for (int z = originZ; z < originZ + 16; ++z) {
                if (noise.getNoise(x, z) <= config.threshold()) {
                    mutable.set(x, world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z) + config.yOffset(), z);
                    System.out.println(mutable.toShortString());
                    System.out.println(world.setBlockState(mutable, config.state().get(random, mutable), 3));
                }
            }
        }
        return true;
    }
}
