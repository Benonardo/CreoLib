package com.github.creoii.creolib.api.world.placement;

import com.github.creoii.creolib.api.registry.StructurePlacementTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FixedStructurePlacement extends StructurePlacement {
    public static final Codec<FixedStructurePlacement> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(BlockPos.CODEC.listOf().fieldOf("positions").forGetter(placement -> {
            return placement.positions;
        })).and(buildCodec(instance)).apply(instance, FixedStructurePlacement::new);
    });
    private final List<BlockPos> positions;

    public FixedStructurePlacement(List<BlockPos> positions, Vec3i locateOffset, FrequencyReductionMethod frequencyReductionMethod, float frequency, int salt, Optional<ExclusionZone> exclusionZone) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, Optional.empty());
        this.positions = new ArrayList<>();
        positions.forEach(pos -> {
            int x = (int) Math.round(pos.getX() / 16d) * 16;
            int z = (int) Math.round(pos.getZ() / 16d) * 16;
            this.positions.add(new BlockPos(x, 0, z));
        });
    }

    protected boolean isStartChunk(StructurePlacementCalculator calculator, int chunkX, int chunkZ) {
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        for (BlockPos pos : positions) {
            if (chunkPos.getStartPos().equals(pos)) return true;
        }
        return false;
    }

    public StructurePlacementType<?> getType() {
        return StructurePlacementTypeRegistry.FIXED;
    }
}
