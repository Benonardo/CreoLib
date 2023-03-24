package com.github.creoii.creolib.api.util.provider.floatprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.floatprovider.FloatProviderType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BlockStateFloatProvider extends FloatProvider {
    public static final Codec<BlockStateFloatProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, BlockStateFloatProvider::new);
    });
    private final String key;
    private World world;
    private BlockState state;
    private BlockPos pos;

    public BlockStateFloatProvider(String key) {
        this.key = key;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setState(BlockState state) {
        this.state = state;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public float get(Random random) {
        return switch (key) {
            case "ambient_occlusion_light_level" -> world != null && pos != null ? state.getAmbientOcclusionLightLevel(world, pos) : -1f;
            case "hardness" -> world != null && pos != null ? ((AbstractBlockSettingsAccessor) state.getBlock()).getHardness() : -1f;
            case "resistance" -> world != null && pos != null ? ((AbstractBlockSettingsAccessor) state.getBlock()).getResistance() : -1f;
            case "slipperiness" -> world != null && pos != null ? ((AbstractBlockSettingsAccessor) state.getBlock()).getSlipperiness() : -1f;
            case "jump_velocity_multiplier" -> world != null && pos != null ? ((AbstractBlockSettingsAccessor) state.getBlock()).getJumpVelocityMultiplier() : -1f;
            case "velocity_multiplier" -> world != null && pos != null ? ((AbstractBlockSettingsAccessor) state.getBlock()).getVelocityMultiplier() : -1f;
            default -> 1f;
        };
    }

    @Override
    public float getMin() {
        return Float.MIN_VALUE;
    }

    @Override
    public float getMax() {
        return Float.MAX_VALUE;
    }

    @Override
    public FloatProviderType<?> getType() {
        return ProvidersRegistry.BLOCKSTATE_FLOAT;
    }
}
