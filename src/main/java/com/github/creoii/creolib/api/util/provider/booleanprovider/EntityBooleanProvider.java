package com.github.creoii.creolib.api.util.provider.booleanprovider;

import com.github.creoii.creolib.api.registry.ProvidersRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.IntProviderType;
import net.minecraft.util.math.random.Random;

public class EntityBooleanProvider extends BooleanProvider {
    public static final Codec<EntityBooleanProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.STRING.fieldOf("key").forGetter(provider -> {
                return provider.key;
            })).apply(instance, EntityBooleanProvider::new);
    });
    private final String key;
    private Entity entity;

    public EntityBooleanProvider(String key) {
        this.key = key;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean get(Random random) {
        if (entity instanceof PlayerEntity player) {
            return switch (key) {
                case "can_be_hit_by_projectile" -> player.canBeHitByProjectile();
                case "can_food_heal" -> player.canFoodHeal();
                case "can_modify_blocks" -> player.canModifyBlocks();
                case "can_reset_time_by_sleeping" -> player.canResetTimeBySleeping();
                case "has_reduced_debug_info" -> player.hasReducedDebugInfo();
                case "is_creative" -> player.isCreative();
                case "is_creative_level_two_op" -> player.isCreativeLevelTwoOp();
                case "is_main_player" -> player.isMainPlayer();
                case "is_spectator" -> player.isSpectator();
                case "is_using_spylass" -> player.isUsingSpyglass();
                case "should_cancel_interaction" -> player.shouldCancelInteraction();
                case "should_filter_text" -> player.shouldFilterText();
                case "should_render_name" -> player.shouldRenderName();
            };
        }
        if (entity instanceof LivingEntity living) {
            return switch (key) {
                case "is_hand_swinging" -> living.handSwinging;
                case "can_breathe_in_water" -> living.canBreatheInWater();
                case "can_take_damage" -> living.canTakeDamage();
                case "disables_shield" -> living.disablesShield();
                case "has_no_drag" -> living.hasNoDrag();
                case "hurt_by_water" -> living.hurtByWater();
                case "is_affected_by_splash_potions" -> living.isAffectedBySplashPotions();
                case "is_baby" -> living.isBaby();
                case "is_blocking" -> living.isBlocking();
                case "is_climbing" -> living.isClimbing();
                case "is_dead" -> living.isDead();
                case "is_experience_dropping_disabled" -> living.isExperienceDroppingDisabled();
                case "is_fall_flying" -> living.isFallFlying();
                case "is_holding_onto_ladder" -> living.isHoldingOntoLadder();
                case "is_mob_or_player" -> living.isMobOrPlayer();
                case "is_pushable" -> living.isPushable();
                case "is_sleeping" -> living.isSleeping();
                case "is_undead" -> living.isUndead();
                case "is_using_item" -> living.isUsingItem();
                case "is_using_riptide" -> living.isUsingRiptide();
            };
        }
        return switch (key) {
            case "is_living" -> entity.isLiving();
            case "collided_softly" -> entity.collidedSoftly;
            case "horizontal_collision" -> entity.horizontalCollision;
            case "ignore_camera_frustum" -> entity.ignoreCameraFrustum;
            case "in_powder_snow" -> entity.inPowderSnow;
            case "no_clip" -> entity.noClip;
            case "velocity_modified" -> entity.velocityModified;
            case "vertical_collision" -> entity.verticalCollision;
            case "has_no_gravity" -> entity.hasNoGravity();
            case "bypasses_landing_effects" -> entity.bypassesLandingEffects();
            case "bypasses_stepping_effects" -> entity.bypassesSteppingEffects();
            case "can_avoid_traps" -> entity.canAvoidTraps();
            case "can_be_hit_by_projectile" -> entity.canBeHitByProjectile();
            case "can_hit" -> entity.canHit();
            case "can_freeze" -> entity.canFreeze();
            case "can_move_voluntarily" -> entity.canMoveVoluntarily();
            case "can_sprint_as_vehicle" -> entity.canSprintAsVehicle();
            case "can_use_portals" -> entity.canUsePortals();
            case "cannot_be_silenced" -> entity.cannotBeSilenced();
            case "does_render_on_fire" -> entity.doesRenderOnFire();
            case "is_alive" -> entity.isAlive();
            case "is_attackable" -> entity.isAttackable();
            case "is_collidable" -> entity.isCollidable();
            case "is_crawling" -> entity.isCrawling();
            case "is_sprinting" -> entity.isSprinting();
            case "is_custom_name_visible" -> entity.isCustomNameVisible();
            case "is_descending" -> entity.isDescending();
            case "is_fire_immune" -> entity.isFireImmune();
            case "is_glowing" -> entity.isGlowing();
            case "is_sneaking" -> entity.isSneaking();
            case "is_sneaky" -> entity.isSneaky();
            case "is_in_lava" -> entity.isInLava();
            case "is_inside_wall" -> entity.isInsideWall();
            case "is_in_sneaking_pose" -> entity.isInSneakingPose();
            case "is_in_swimming_pose" -> entity.isInSwimmingPose();
            case "is_inside_water_or_bubble_column" -> entity.isInsideWaterOrBubbleColumn();
            case "is_invisible" -> entity.isInvisible();
            case "is_immune_to_explosion" -> entity.isImmuneToExplosion();
            case "is_invulnerable" -> entity.isInvulnerable();
            case "is_player" -> entity.isPlayer();
        };
    }

    @Override
    public BooleanProviderType<?> getType() {
        return ProvidersRegistry.ENTITY_BOOLEAN;
    }
}
