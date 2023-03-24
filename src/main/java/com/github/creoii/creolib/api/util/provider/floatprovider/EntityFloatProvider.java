package com.github.creoii.creolib.api.util.provider.floatprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.floatprovider.FloatProviderType;
import net.minecraft.util.math.random.Random;

public class EntityFloatProvider extends FloatProvider {
    public static final Codec<EntityFloatProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, EntityFloatProvider::new);
    });
    private final String key;
    private Entity entity;

    public EntityFloatProvider(String key) {
        this.key = key;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public float get(Random random) {
        if (entity instanceof PlayerEntity player) {
            return switch (key) {
                case "stride_distance" -> player.strideDistance;
                default -> 1f;
            };
        }
        if (entity instanceof LivingEntity living) {
            return switch (key) {
                case "forward_speed" -> living.forwardSpeed;
                case "random_large_seed" -> living.randomLargeSeed;
                case "random_small_seed" -> living.randomSmallSeed;
                case "sideways_speed" -> living.sidewaysSpeed;
                case "upward_speed" -> living.upwardSpeed;
                case "damage_tilt_yaw" -> living.getDamageTiltYaw();
                case "health" -> living.getHealth();
                case "jump_boost_velocity_modifier" -> (float) living.getJumpBoostVelocityModifier();
                case "absorption_amount" -> living.getAbsorptionAmount();
                case "armor_visibility" -> living.getArmorVisibility();
                case "scale_factor" -> living.getScaleFactor();
                case "sound_pitch" -> living.getSoundPitch();
                case "max_health" -> living.getMaxHealth();
                case "movement_speed" -> living.getMovementSpeed();
                default -> 1f;
            };
        }
        return switch (key) {
            case "distance_traveled" -> entity.distanceTraveled;
            case "fall_distance" -> entity.fallDistance;
            case "horizontal_speed" -> entity.horizontalSpeed;
            case "body_yaw" -> entity.getBodyYaw();
            case "freezing_scale" -> entity.getFreezingScale();
            case "head_Yaw" -> entity.getHeadYaw();
            case "speed" -> entity.speed;
            case "pitch" -> entity.getPitch();
            case "standing_eye_height" -> entity.getStandingEyeHeight();
            case "width" -> entity.getWidth();
            case "yaw" -> entity.getYaw();
            case "height" -> entity.getHeight();
            case "step_height" -> entity.getStepHeight();
            case "eye_y" -> (float) entity.getEyeY();
            case "y" -> (float) entity.getY();
            case "height_offset" -> (float) entity.getHeightOffset();
            case "mounted_height_offset" -> (float) entity.getMountedHeightOffset();
            case "x" -> (float) entity.getX();
            case "z" -> (float) entity.getZ();
            case "random_body_y" -> (float) entity.getRandomBodyY();
            case "swim_height" -> (float) entity.getSwimHeight();
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
        return ProvidersRegistry.ENTITY_FLOAT;
    }
}
