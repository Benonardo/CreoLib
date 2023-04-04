package com.github.creoii.creolib.api.registry;

import com.github.creoii.creolib.core.CreoLib;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class AttributeRegistry {
    // controls the amount y velocity is decreased over time
    public static final EntityAttribute GENERIC_GRAVITY = new ClampedEntityAttribute("attribute.name.generic.gravity", .08d, 0d, 16d).setTracked(true);
    // controls entity speed while swimming
    public static final EntityAttribute GENERIC_SWIM_SPEED = new ClampedEntityAttribute("attribute.name.generic.swim_speed", 1d, 0d, 1024d).setTracked(true);
    // controls the interaction range when attacking entities
    public static final EntityAttribute GENERIC_ATTACK_RANGE = new ClampedEntityAttribute("attribute.name.generic.attack_range", 3d, 0d, 256d).setTracked(true);
    // controls entity display size
    public static final EntityAttribute GENERIC_SCALE = new ClampedEntityAttribute("attribute.name.generic.scale", 1d, .1d, 16d).setTracked(true);
    // controls the general interaction range
    public static final EntityAttribute PLAYER_REACH_DISTANCE = new ClampedEntityAttribute("attribute.name.player.reach_distance", 4.5d, 0d, 256d).setTracked(true);
    // controls the block placing cooldown
    public static final EntityAttribute PLAYER_BLOCK_PLACE_SPEED = new ClampedEntityAttribute("attribute.name.player.block_place_speed", 4d, 0d, 256d).setTracked(true);
    // controls the block breaking cooldown
    public static final EntityAttribute PLAYER_BLOCK_BREAK_SPEED = new ClampedEntityAttribute("attribute.name.player.block_break_speed", 5d, 0d, 256d).setTracked(true);
    // controls the speed while flying
    public static final EntityAttribute PLAYER_FLIGHT_SPEED = new ClampedEntityAttribute("attribute.name.player.flight_speed", .05d, 0d, 1024d).setTracked(true);

    public static void register() {
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "generic.gravity"), GENERIC_GRAVITY);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "generic.swim_speed"), GENERIC_SWIM_SPEED);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "generic.attack_range"), GENERIC_ATTACK_RANGE);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "generic.scale"), GENERIC_SCALE);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "player.reach_distance"), PLAYER_REACH_DISTANCE);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "player.block_place_cooldown"), PLAYER_BLOCK_PLACE_SPEED);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "player.block_break_cooldown"), PLAYER_BLOCK_BREAK_SPEED);
        Registry.register(Registries.ATTRIBUTE, new Identifier(CreoLib.NAMESPACE, "player.flight_speed"), PLAYER_FLIGHT_SPEED);
    }
}
