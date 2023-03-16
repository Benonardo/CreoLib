package com.github.creoii.creolib.api.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

import java.util.List;

/**
 * Extension of {@link FoodComponent} allowing custom eating speeds
 */
public class CFoodComponent extends FoodComponent {
    private final int eatTime;

    public CFoodComponent(int hunger, float saturationModifier, boolean meat, boolean alwaysEdible, boolean snack, int eatTime, List<Pair<StatusEffectInstance, Float>> statusEffects) {
        super(hunger, saturationModifier, meat, alwaysEdible, snack, statusEffects);
        this.eatTime = eatTime;
    }

    public CFoodComponent(int hunger, float saturationModifier, boolean meat, boolean alwaysEdible, boolean snack, int eatTime) {
        this(hunger, saturationModifier, meat, alwaysEdible, snack, eatTime, List.of());
    }

    public int getEatTime() {
        return eatTime;
    }

    public static class Builder {
        private int hunger;
        private float saturationModifier;
        private boolean meat;
        private boolean alwaysEdible;
        private boolean snack;
        private int eatTime;
        private final List<Pair<StatusEffectInstance, Float>> statusEffects = Lists.newArrayList();

        public CFoodComponent.Builder hunger(int hunger) {
            this.hunger = hunger;
            return this;
        }

        public CFoodComponent.Builder saturationModifier(float saturationModifier) {
            this.saturationModifier = saturationModifier;
            return this;
        }

        public CFoodComponent.Builder meat() {
            meat = true;
            return this;
        }

        public CFoodComponent.Builder alwaysEdible() {
            alwaysEdible = true;
            return this;
        }

        public CFoodComponent.Builder snack() {
            snack = true;
            return this;
        }

        public CFoodComponent.Builder eatTime(int eatTime) {
            this.eatTime = eatTime;
            return this;
        }

        public CFoodComponent build() {
            return new CFoodComponent(hunger, saturationModifier, meat, alwaysEdible, snack, eatTime, statusEffects);
        }
    }
}
