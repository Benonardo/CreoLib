package com.github.creoii.creolib.api.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TagUtil {
    public static boolean isEnchantmentIn(Enchantment enchantment, RegistryEntryList<Enchantment> entryList) {
        RegistryEntry<Enchantment> entry = Registries.ENCHANTMENT.getEntry(enchantment);
        if (entry.hasKeyAndValue()) {
            return entryList.contains(entry);
        } return false;
    }

    public static boolean isEnchantmentIn(Enchantment enchantment, TagKey<Enchantment> tag) {
        RegistryEntry<Enchantment> entry = Registries.ENCHANTMENT.getEntry(enchantment);
        if (entry.hasKeyAndValue()) {
            return entry.isIn(tag);
        } return false;
    }

    public static boolean isStatusEffectIn(StatusEffect statusEffect, RegistryEntryList<StatusEffect> entryList) {
        RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(statusEffect);
        if (entry.hasKeyAndValue()) {
            return entryList.contains(entry);
        } return false;
    }

    public static boolean isStatusEffectIn(StatusEffect statusEffect, TagKey<StatusEffect> tag) {
        RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(statusEffect);
        if (entry.hasKeyAndValue()) {
            return entry.isIn(tag);
        } return false;
    }

    public static boolean isPaintingIn(PaintingVariant painting, RegistryEntryList<PaintingVariant> entryList) {
        RegistryEntry<PaintingVariant> entry = Registries.PAINTING_VARIANT.getEntry(painting);
        if (entry.hasKeyAndValue()) {
            return entryList.contains(entry);
        } return false;
    }

    public static boolean isPaintingIn(PaintingVariant painting, TagKey<PaintingVariant> tag) {
        RegistryEntry<PaintingVariant> entry = Registries.PAINTING_VARIANT.getEntry(painting);
        if (entry.hasKeyAndValue()) {
            return entry.isIn(tag);
        } return false;
    }

    /**
     * @return A list of registry entries present in {@param tagKey}
     * This method will return an empty list if used before tags are dynamically loaded,
     * unless they are loaded with an {@link net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider}
     */
    public static <T> List<RegistryEntry<T>> getEntries(Registry<T> registry, TagKey<T> tagKey) {
        List<RegistryEntry<T>> entries = new ArrayList<>();
        registry.iterateEntries(tagKey).forEach(entries::add);
        return entries;
    }
}
