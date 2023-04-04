package com.github.creoii.creolib.api.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

public class QuarteredBlock extends Block {
    public static final IntProperty QUARTERS = IntProperty.of("quarters", 1, 255);
    public static final VoxelShape[] SIZE_PIECES = Util.make(new VoxelShape[8], voxelShapes -> {
        voxelShapes[0] = VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.5, 0.5, 0.5);
        voxelShapes[1] = VoxelShapes.cuboid(0.5, 0.0, 0.0, 1.0, 0.5, 0.5);
        voxelShapes[2] = VoxelShapes.cuboid(0.0, 0.0, 0.5, 0.5, 0.5, 1.0);
        voxelShapes[3] = VoxelShapes.cuboid(0.5, 0.0, 0.5, 1.0, 0.5, 1.0);
        voxelShapes[4] = VoxelShapes.cuboid(0.0, 0.5, 0.0, 0.5, 1.0, 0.5);
        voxelShapes[5] = VoxelShapes.cuboid(0.5, 0.5, 0.0, 1.0, 1.0, 0.5);
        voxelShapes[6] = VoxelShapes.cuboid(0.0, 0.5, 0.5, 0.5, 1.0, 1.0);
        voxelShapes[7] = VoxelShapes.cuboid(0.5, 0.5, 0.5, 1.0, 1.0, 1.0);
    });
    public static final VoxelShape[] SIZES = Util.make(new VoxelShape[256], voxelShapes -> {
        for (int i = 0; i < voxelShapes.length; ++i) {
            VoxelShape voxelShape = VoxelShapes.empty();
            for (int j = 0; j < 8; ++j) {
                if (QuarteredBlock.method_50856(i, j)) continue;
                voxelShape = VoxelShapes.union(voxelShape, SIZE_PIECES[j]);
            }
            voxelShapes[i] = voxelShape.simplify();
        }
    });

    protected QuarteredBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(QUARTERS, 255));
    }

    private static boolean method_50856(int i, int j) {
        return (i & QuarteredBlock.method_50859(j)) == 0;
    }

    private static int method_50859(int i) {
        return 1 << i;
    }

    private static int getSize(int i, int j) {
        return i & ~QuarteredBlock.method_50859(j);
    }

    private static boolean isFullSize(BlockState blockState) {
        return blockState.get(QUARTERS) == 255;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getStackInHand(hand).isEmpty()) {
            return ActionResult.FAIL;
        }
        Vec3d vec3d = hit.getPos().subtract(pos.getX(), pos.getY(), pos.getZ());
        int i = QuarteredBlock.method_50857(state, vec3d);
        if (i == -1) {
            return ActionResult.FAIL;
        }
        int j = QuarteredBlock.getSize(state.get(QUARTERS), i);
        if (j != 0) {
            world.setBlockState(pos, state.with(QUARTERS, j));
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }
        return ActionResult.SUCCESS;
    }

    private static int method_50857(BlockState blockState, Vec3d vec3d) {
        int i = blockState.get(QUARTERS);
        double d = Double.MAX_VALUE;
        int j = -1;
        for (int k = 0; k < SIZE_PIECES.length; ++k) {
            double e;
            Optional<Vec3d> optional;
            if (QuarteredBlock.method_50856(i, k) || !(optional = SIZE_PIECES[k].getClosestPointTo(vec3d)).isPresent() || !((e = optional.get().squaredDistanceTo(vec3d)) < d)) continue;
            d = e;
            j = k;
        }
        return j;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SIZES[state.get(QUARTERS)];
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return QuarteredBlock.isFullSize(state) ? .2f : 1f;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(QUARTERS);
    }
}