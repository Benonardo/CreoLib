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
        Optional<TagKey<Enchantment>> tag = entryList.getTagKey();
        if (entry.hasKeyAndValue() && tag.isPresent()) {
            return entry.isIn(tag.get());
        } return false;
    }

    public static boolean isStatusEffectIn(StatusEffect statusEffect, RegistryEntryList<StatusEffect> entryList) {
        RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(statusEffect);
        Optional<TagKey<StatusEffect>> tag = entryList.getTagKey();
        if (entry.hasKeyAndValue() && tag.isPresent()) {
            return entry.isIn(tag.get());
        } return false;
    }

    public static boolean isPaintingIn(PaintingVariant painting, RegistryEntryList<PaintingVariant> entryList) {
        RegistryEntry<PaintingVariant> entry = Registries.PAINTING_VARIANT.getEntry(painting);
        Optional<TagKey<PaintingVariant>> tag = entryList.getTagKey();
        if (entry.hasKeyAndValue() && tag.isPresent()) {
            return entry.isIn(tag.get());
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
