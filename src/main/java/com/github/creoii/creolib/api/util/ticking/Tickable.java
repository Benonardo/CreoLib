package com.github.creoii.creolib.api.util.ticking;

import net.minecraft.world.World;

@FunctionalInterface
public interface Tickable {
    void tick(World world);
}
