package com.github.creoii.creolib.api.util.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public final class ItemRegistryHelper {
    /**
     * Register an item
     * Optionally, add the item to an itemgroup after an item
     * @param after the {@link ItemConvertible} to add after
     * @param group -the itemgroup to add to
     */
    public static void registerItem(Identifier id, Item item, @Nullable ItemConvertible after, @Nullable ItemGroup group) {
        Registry.register(Registries.ITEM, id, item);
        if (group != null) {
            if (after != null) {
                ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAfter(after, item));
            } else {
                ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
            }
        }
    }

    /**
     * Register an item into multiple ItemGroups
     * @param groups itemGroups to add to
     */
    public static void registerItem(Identifier id, Item item, ItemGroupSettings... groups) {
        Registry.register(Registries.ITEM, id, item);
        if (groups != null) {
            for (ItemGroupSettings settings : groups) {
                if (settings.after() != null) {
                    ItemGroupEvents.modifyEntriesEvent(settings.group()).register(entries -> entries.addAfter(settings.after(), item));
                } else {
                    ItemGroupEvents.modifyEntriesEvent(settings.group()).register(entries -> entries.add(item));
                }
            }
        }
    }

    /**
     * @param group the itemgroup to add to
     * @param after optionally, the item to add after
     */
    public record ItemGroupSettings(ItemGroup group, @Nullable ItemConvertible after) { }
}
