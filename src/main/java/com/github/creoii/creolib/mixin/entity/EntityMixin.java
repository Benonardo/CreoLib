package com.github.creoii.creolib.mixin.entity;

import com.github.creoii.creolib.api.entity.ScalableEntity;
import com.github.creoii.creolib.api.tag.CBlockTags;
import com.github.creoii.creolib.api.tag.CEntityTypeTags;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract BlockState getBlockStateAtPos();
    @Shadow public abstract EntityType<?> getType();
    @Shadow public abstract World getWorld();
    @Shadow protected abstract void checkBlockCollision();
    @Shadow public abstract void onLanding();
    @Shadow public abstract boolean isInLava();
    @Shadow public abstract boolean isTouchingWater();
    @Shadow public abstract BlockPos getBlockPos();
    @Shadow public abstract Vec3d getVelocity();
    @Shadow public abstract void setVelocity(Vec3d velocity);
    @Shadow public abstract double getX();
    @Shadow public abstract double getZ();
    @Shadow public abstract boolean isSubmergedInWater();
    @Shadow public abstract boolean isDescending();
    @Shadow public abstract boolean isSpectator();
    @Shadow protected abstract float getEyeHeight(EntityPose pose, EntityDimensions dimensions);
    @Shadow public abstract EntityPose getPose();
    @Shadow protected boolean onGround;
    @Shadow public boolean noClip;
    @Shadow private EntityDimensions dimensions;
    @Shadow private float standingEyeHeight;

    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);

    @Shadow public abstract double getY();

    private static final ImmutableMap<RegistryKey<DamageType>, TagKey<EntityType<?>>> DAMAGE_IMMUNITIES = new ImmutableMap.Builder<RegistryKey<DamageType>, TagKey<EntityType<?>>>()
            .put(DamageTypes.GENERIC, CEntityTypeTags.GENERIC_IMMUNE)
            .put(DamageTypes.FALL, CEntityTypeTags.FALL_IMMUNE)
            .put(DamageTypes.CACTUS, CEntityTypeTags.CACTUS_IMMUNE)
            .put(DamageTypes.SWEET_BERRY_BUSH, CEntityTypeTags.SWEET_BERRY_BUSH_IMMUNE)
            .put(DamageTypes.STALAGMITE, CEntityTypeTags.DRIPSTONE_IMMUNE)
            .put(DamageTypes.LIGHTNING_BOLT, CEntityTypeTags.LIGHTNING_IMMUNE)
            .put(DamageTypes.WITHER, CEntityTypeTags.WITHER_IMMUNE)
            .put(DamageTypes.MAGIC, CEntityTypeTags.MAGIC_IMMUNE)
            .put(DamageTypes.DROWN, CEntityTypeTags.DROWNING_IMMUNE)
            .put(DamageTypes.IN_FIRE, CEntityTypeTags.FIRE_IMMUNE)
            .put(DamageTypes.ON_FIRE, CEntityTypeTags.FIRE_IMMUNE)
            .put(DamageTypes.IN_WALL, CEntityTypeTags.SUFFOCATION_IMMUNE)
            .put(DamageTypes.FLY_INTO_WALL, CEntityTypeTags.FLY_INTO_WALL_IMMUNE)
            .put(DamageTypes.STARVE, CEntityTypeTags.STARVATION_IMMUNE)
            .put(DamageTypes.CRAMMING, CEntityTypeTags.CRAMMING_IMMUNE)
            .put(DamageTypes.OUT_OF_WORLD, CEntityTypeTags.OUT_OF_WORLD_IMMUNE)
            .put(DamageTypes.DRY_OUT, CEntityTypeTags.DRYOUT_IMMUNE)
            .build();

    @Inject(method = "tick", at = @At("HEAD"))
    private void creo_lib_blockNoClipTick(CallbackInfo ci) {
        if (getType().isIn(CEntityTypeTags.NO_CLIPPING_ENTITIES)) {
            noClip = !getBlockStateAtPos().isIn(CBlockTags.BLOCKS_NO_CLIPPING);
        }
    }

    @Inject(method = "move", at = @At("HEAD"))
    private void creo_lib_blockNoClipMove(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        if (getType().isIn(CEntityTypeTags.NO_CLIPPING_ENTITIES)) {
            noClip = !getBlockStateAtPos().isIn(CBlockTags.BLOCKS_NO_CLIPPING);
        }
    }

    @Inject(method = "isInsideWall", at = @At("HEAD"))
    private void creo_lib_blockNoClipInsideWall(CallbackInfoReturnable<Boolean> cir) {
        if (getType().isIn(CEntityTypeTags.NO_CLIPPING_ENTITIES)) {
            noClip = !getBlockStateAtPos().isIn(CBlockTags.BLOCKS_NO_CLIPPING);
        }
    }

    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void creo_lib_damageImmunities(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Optional<RegistryKey<DamageType>> damageTypeKey = damageSource.getTypeRegistryEntry().getKey();
        if (damageTypeKey.isPresent() && DAMAGE_IMMUNITIES.containsKey(damageTypeKey.get())) {
            cir.setReturnValue(getType().isIn(DAMAGE_IMMUNITIES.get(damageTypeKey.get())));
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void creo_lib_walkOnFluids(CallbackInfo ci) {
        if (!isSpectator()) {
            if (getType().isIn(CEntityTypeTags.WALKS_ON_WATER) || getType().isIn(CEntityTypeTags.WALKS_ON_LAVA)) {
                ShapeContext shapeContext = ShapeContext.of((Entity) (Object) this);
                if ((isInLava() || (isTouchingWater() && !isSubmergedInWater())) && !isDescending()) {
                    if (!shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, getBlockPos(), true) || getWorld().getFluidState(getBlockPos().up()).isIn(isTouchingWater() ? FluidTags.WATER : FluidTags.LAVA)) {
                        setVelocity(getVelocity().multiply(.5d).add(0d, .05d, 0d));
                    } else onGround = true;
                }
                checkBlockCollision();
            }
        }
    }

    @Inject(method = "fall", at = @At("TAIL"), cancellable = true)
    private void creo_lib_landOnFluids(CallbackInfo ci) {
        if (!isSpectator()) {
            if (getType().isIn(CEntityTypeTags.WALKS_ON_WATER) || getType().isIn(CEntityTypeTags.WALKS_ON_LAVA)) {
                checkBlockCollision();
                if ((isInLava() && getType().isIn(CEntityTypeTags.WALKS_ON_LAVA)) || ((isTouchingWater() && !isSubmergedInWater()) && getType().isIn(CEntityTypeTags.WALKS_ON_WATER))) {
                    onLanding();
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "canAvoidTraps", at = @At("HEAD"), cancellable = true)
    private void creo_lib_canAvoidTraps(CallbackInfoReturnable<Boolean> cir) {
        if (getType().isIn(CEntityTypeTags.AVOIDS_TRAPS)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isCollidable", at = @At("HEAD"), cancellable = true)
    private void creo_lib_isCollidable(CallbackInfoReturnable<Boolean> cir) {
        if (getType().isIn(CEntityTypeTags.COLLIDABLE)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "calculateDimensions", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;refreshPosition()V"))
    private void creo_lib_scaleDimensions(CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof ScalableEntity scalableEntity) {
            dimensions = dimensions.scaled(scalableEntity.getScale());
            standingEyeHeight = getEyeHeight(getPose(), dimensions) * (getType().isIn(CEntityTypeTags.EYE_HEIGHT_SCALED_LESS) ? 1f : scalableEntity.getScale());
        }
    }

    @Inject(method = "calculateBoundsForPose", at = @At("HEAD"), cancellable = true)
    private void creo_lib_scaleBoundsForPose(EntityPose pos, CallbackInfoReturnable<Box> cir) {
        if (((Entity) (Object) this) instanceof ScalableEntity scalableEntity) {
            EntityDimensions dimensions = getDimensions(pos).scaled(scalableEntity.getScale());
            float f = dimensions.width / 2f;
            Vec3d vec3d = new Vec3d(this.getX() - (double)f, getY(), getZ() - (double)f);
            Vec3d vec3d2 = new Vec3d(this.getX() + (double)f, getY() + (double)dimensions.height, getZ() + (double)f);
            cir.setReturnValue(new Box(vec3d, vec3d2));
        }
    }
}
