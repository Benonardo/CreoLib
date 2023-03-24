package com.github.creoii.creolib.api.util.provider.intprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.IntProviderType;
import net.minecraft.util.math.random.Random;

public class EntityIntProvider extends IntProvider {
    public static final Codec<EntityIntProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, EntityIntProvider::new);
    });
    private final String key;
    private Entity entity;

    public EntityIntProvider(String key) {
        this.key = key;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int get(Random random) {
        if (entity instanceof PlayerEntity player) {
            return switch (key) {
                case "experience_level" -> player.experienceLevel;
                case "next_level_experience" -> player.getNextLevelExperience();
                case "score" -> player.getScore();
                default -> 1;
            };
        }
        if (entity instanceof LivingEntity living) {
            return switch (key) {
                case "death_time" -> living.deathTime;
                case "default_max_health" -> living.defaultMaxHealth;
                case "armor" -> living.getArmor();
                case "roll" -> living.getRoll();
                case "stuck_arrow_count" -> living.getStuckArrowCount();
                case "stinger_count" -> living.getStingerCount();
                case "xp_to_drop" -> living.getXpToDrop();
                case "item_use_time" -> living.getItemUseTime();
                case "last_attacked_time" -> living.getLastAttackedTime();
                case "last_attack_time" -> living.getLastAttackTime();
                default -> 1;
            };
        }
        return switch (key) {
            case "age" -> entity.age;
            case "air" -> entity.getAir();
            case "time_until_regen" -> entity.timeUntilRegen;
            case "block_x" -> entity.getBlockX();
            case "block_y" -> entity.getBlockY();
            case "block_z" -> entity.getBlockZ();
            case "fire_ticks" -> entity.getFireTicks();
            case "frozen_ticks" -> entity.getFrozenTicks();
            case "safe_fall_distance" -> entity.getSafeFallDistance();
            default -> 1;
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
        return ProvidersRegistry.ENTITY_INT;
    }
}
