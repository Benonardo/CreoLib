package com.github.creoii.creolib.api.util.provider.intprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.IntProviderType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class BlockStateIntProvider extends IntProvider {
    public static final Codec<BlockStateIntProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, BlockStateIntProvider::new);
    });
    private final String key;
    private World world;
    private BlockState state;
    private BlockPos pos;

    public BlockStateIntProvider(String key) {
        this.key = key;
    }

    public BlockStateIntProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    public BlockStateIntProvider setState(BlockState state) {
        this.state = state;
        return this;
    }

    public BlockStateIntProvider setPos(BlockPos pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public int get(Random random) {
        return switch (key) {
            case "luminance" -> state.getLuminance();
            case "comparator_output" -> world != null && pos != null ? state.getComparatorOutput(world, pos) : -1;
            case "opacity" -> world != null && pos != null ? state.getOpacity(world, pos) : -1;
            default -> {
                for (Property<?> property : state.getBlock().getStateManager().getProperties().stream().filter(property -> property instanceof IntProperty).toList()) {
                    if (key.equals(property.getName())) {
                        yield state.get((IntProperty) property);
                    }
                }
                yield -1;
            }
        };
    }

    @Override
    public int getMin() {
        return Integer.MIN_VALUE;
    }

    @Override
    public int getMax() {
        return Integer.MAX_VALUE;
    }

    @Override
    public IntProviderType<?> getType() {
        return ProvidersRegistry.BLOCKSTATE_INT;
    }
}
