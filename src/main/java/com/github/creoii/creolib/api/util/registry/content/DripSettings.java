package com.github.creoii.creolib.api.util.registry.content;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;

public record DripSettings(BlockState drippedState, Fluid fluid) {}
