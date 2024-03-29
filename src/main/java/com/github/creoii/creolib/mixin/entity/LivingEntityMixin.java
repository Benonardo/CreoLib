package com.github.creoii.creolib.mixin.entity;

import com.github.creoii.creolib.api.enchantment.EquippableEnchantment;
import com.github.creoii.creolib.api.entity.GlintableEntity;
import com.github.creoii.creolib.api.entity.ScalableEntity;
import com.github.creoii.creolib.api.registry.AttributeRegistry;
import com.github.creoii.creolib.api.tag.CEntityTypeTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements GlintableEntity, ScalableEntity {
    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
    @Shadow protected abstract int computeFallDamage(float fallDistance, float damageMultiplier);
    @Shadow public abstract Vec3d applyFluidMovingSpeed(double gravity, boolean falling, Vec3d motion);
    @Shadow public abstract double getAttributeValue(RegistryEntry<EntityAttribute> attribute);
    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);
    private static final EntityAttributeModifier SLOW_FALLING = new EntityAttributeModifier(UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA"), "Slow falling acceleration reduction", -0.07d, EntityAttributeModifier.Operation.ADDITION);
    private static final TrackedData<Float> SCALE = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
    private static EntityAttributeInstance gravity;
    private boolean hasGlint;
    private float prevScale = 1f;

    private LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setGlinted(boolean hasGlint) {
        this.hasGlint = hasGlint;
    }

    @Override
    public boolean hasGlint() {
        return hasGlint;
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void creo_lib_createNewAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(AttributeRegistry.GENERIC_GRAVITY).add(AttributeRegistry.GENERIC_SWIM_SPEED, .03999999910593033d).add(AttributeRegistry.GENERIC_ATTACK_RANGE).add(AttributeRegistry.GENERIC_SCALE, 1d).add(AttributeRegistry.GENERIC_JUMP_STRENGTH, .42d);
    }

    @Inject(method = "knockDownwards", at = @At("HEAD"), cancellable = true)
    private void creo_lib_applyKnockbackSwimSpeed(CallbackInfo ci) {
        setVelocity(getVelocity().add(0d, -.03999999910593033d * getAttributeValue(AttributeRegistry.GENERIC_GRAVITY), 0d));
        ci.cancel();
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyFluidMovingSpeed(DZLnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d creo_lib_applyGravity(LivingEntity entity, double d, boolean bl, Vec3d vec3d) {
        return applyFluidMovingSpeed(entity.getAttributeValue(AttributeRegistry.GENERIC_GRAVITY), bl, vec3d);
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private double creo_lib_modifyGravityVariable(double d) {
        gravity = getAttributeInstance(AttributeRegistry.GENERIC_GRAVITY);
        if (hasStatusEffect(StatusEffects.SLOW_FALLING) && !gravity.hasModifier(SLOW_FALLING)) gravity.addTemporaryModifier(SLOW_FALLING);
        return gravity.getValue();
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;", shift = At.Shift.BEFORE))
    private void creo_lib_removeSlowFallingModifier(Vec3d movementInput, CallbackInfo ci) {
        if (gravity.hasModifier(SLOW_FALLING)) gravity.removeModifier(SLOW_FALLING);
    }

    @Redirect(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;computeFallDamage(FF)I"))
    private int creo_lib_handleFallDamageGravity(LivingEntity entity, float fallDistance, float damageMultiplier) {
        return computeFallDamage(fallDistance * (float) (entity.getAttributeValue(AttributeRegistry.GENERIC_GRAVITY) * 12.5f), damageMultiplier);
    }

    @Inject(method = "swimUpward", at = @At("HEAD"), cancellable = true)
    private void creo_lib_applyUpwardSwimSpeed(TagKey<Fluid> fluid, CallbackInfo ci) {
        setVelocity(getVelocity().add(0d, getAttributeValue(AttributeRegistry.GENERIC_SWIM_SPEED), 0d));
        ci.cancel();
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"))
    private void creo_lib_applySwimSpeed(LivingEntity entity, float speed, Vec3d movementInput) {
        speed *= getAttributeValue(AttributeRegistry.GENERIC_SWIM_SPEED);
        updateVelocity(speed, movementInput);
    }

    @Inject(method = "onEquipStack", at = @At("TAIL"))
    private void creo_lib_applyEquippableEnchantments(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
        Map<Enchantment, Integer> unequipped = EnchantmentHelper.get(oldStack);
        unequipped.keySet().forEach(enchantment -> {
            if (enchantment instanceof EquippableEnchantment equippableEnchantment) {
                equippableEnchantment.onUnequip(world, this, oldStack, slot, unequipped.get(enchantment));
            }
        });
        Map<Enchantment, Integer> equipped = EnchantmentHelper.get(newStack);
        equipped.keySet().forEach(enchantment -> {
            if (enchantment instanceof EquippableEnchantment equippableEnchantment) {
                equippableEnchantment.onEquip(world, this, oldStack, slot, equipped.get(enchantment));
            }
        });
    }

    @Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
    private void creo_lib_entityGroupTags(CallbackInfoReturnable<EntityGroup> cir) {
        if (getType().isIn(CEntityTypeTags.UNDEAD)) {
            cir.setReturnValue(EntityGroup.UNDEAD);
        } else if (getType().isIn(CEntityTypeTags.AQUATIC)) {
            cir.setReturnValue(EntityGroup.AQUATIC);
        } else if (getType().isIn(CEntityTypeTags.ARTHROPOD)) {
            cir.setReturnValue(EntityGroup.ARTHROPOD);
        } else if (getType().isIn(CEntityTypeTags.ILLAGER)) {
            cir.setReturnValue(EntityGroup.ILLAGER);
        }
    }

    @Inject(method = "canBreatheInWater", at = @At("HEAD"), cancellable = true)
    private void creo_lib_canBreatheInWater(CallbackInfoReturnable<Boolean> cir) {
        if (getType().isIn(CEntityTypeTags.CAN_BREATHE_IN_WATER))
            cir.setReturnValue(true);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
    private void creo_lib_updateDimensionCalculation(CallbackInfo ci) {
        if (prevScale != getScale()) {
            getDimensions(getPose()).fixed = false;
            calculateDimensions();
            prevScale = getScale();
        }
    }

    @Override
    public void setScale(float scale) {
        dataTracker.set(SCALE, scale);
    }

    public float getScale() {
        return (float)getAttributeValue(AttributeRegistry.GENERIC_SCALE);
    }

    private float getTrackedScale() {
        return dataTracker.get(SCALE);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void creo_lib_trackScale(CallbackInfo ci) {
        dataTracker.startTracking(SCALE, 1f);
    }

    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    private void creo_lib_onSetScale(TrackedData<?> data, CallbackInfo ci) {
        if (SCALE.equals(data)) {
            getAttributeInstance(AttributeRegistry.GENERIC_SCALE).setBaseValue(getTrackedScale());
            calculateDimensions();
        }
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void creo_lib_scaleDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        cir.setReturnValue(cir.getReturnValue().scaled(getTrackedScale()));
    }

    @ModifyConstant(method = "getJumpVelocity", constant = @Constant(floatValue = .42f))
    private float creo_lib_applyJumpStrength(float constant) {
        return (float) getAttributeValue(AttributeRegistry.GENERIC_JUMP_STRENGTH);
    }

    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void creo_lib_adjustStepHeightForScale(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValue() * ((float) getAttributeValue(AttributeRegistry.GENERIC_SCALE) / 2f));
    }
}