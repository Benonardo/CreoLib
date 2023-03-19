package com.github.creoii.creolib.api.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public final class WorldUtil {
    public static void broadcast(@NotNull World world, Text text, Predicate<PlayerEntity> playerEntityPredicate) {
        world.getPlayers().forEach(playerEntity -> {
            if (playerEntityPredicate.test(playerEntity)) {
                playerEntity.sendMessage(text);
            }
        });
    }

    public static void broadcast(World world, Text text) {
        broadcast(world, text, playerEntity -> true);
    }
}
