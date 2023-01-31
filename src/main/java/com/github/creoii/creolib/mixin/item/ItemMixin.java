package com.github.creoii.creolib.mixin.item;

import com.github.creoii.creolib.util.CFoodComponent;
import com.github.creoii.creolib.util.duck.ItemSettingsDuck;
import com.github.creoii.creolib.util.registry.CItemSettings;
import com.github.creoii.creolib.util.registry.ItemRegistryHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.VillagerInteractionRegistries;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemSettingsDuck {
    @Shadow @Nullable public abstract FoodComponent getFoodComponent();
    @Mutable @Shadow @Final private @Nullable FoodComponent foodComponent;

    private Item.Settings settings;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void creo_lib_accessItemSettings(Item.Settings settings, CallbackInfo ci) {
        this.settings = settings;
        if (settings instanceof CItemSettings cSettings) {
            this.foodComponent = cSettings.getFoodComponent();
            if (cSettings.getFuelPower() > 0) {
                FuelRegistry.INSTANCE.add((Item) (Object) this, cSettings.getFuelPower());
            }
            if (cSettings.getCompostingChance() > 0f) {
                CompostingChanceRegistry.INSTANCE.add((ItemConvertible) this, cSettings.getCompostingChance());
            }
            if (cSettings.isVillagerCollectable()) {
                VillagerInteractionRegistries.registerCollectable((ItemConvertible) this);
            }
            if (cSettings.isFarmerCompostable()) {
                VillagerInteractionRegistries.registerCompostable((ItemConvertible) this);
            }
            if (cSettings.getVillagerFoodValue() > 0) {
                VillagerInteractionRegistries.registerFood((ItemConvertible) this, cSettings.getVillagerFoodValue());
            }
            if (cSettings.getItemGroups() != null) {
                for (ItemRegistryHelper.ItemGroupSettings itemGroupSettings : cSettings.getItemGroups()) {
                    if (itemGroupSettings.after() != null) {
                        ItemGroupEvents.modifyEntriesEvent(itemGroupSettings.group()).register(entries -> entries.addAfter(itemGroupSettings.after(), (Item) (Object) this));
                    } else {
                        ItemGroupEvents.modifyEntriesEvent(itemGroupSettings.group()).register(entries -> entries.add((Item) (Object) this));
                    }
                }
            }
        }
    }

    @Override
    public Item.Settings getItemSettings() {
        return settings;
    }

    @Inject(method = "getMaxUseTime", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void creo_lib_foodEatingTimes(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (getFoodComponent() instanceof CFoodComponent cfoodComponent) {
            cir.setReturnValue(cfoodComponent.getEatTime());
        }
    }
}
