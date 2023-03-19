package com.github.creoii.creolib.api.util.registry;

import com.github.creoii.creolib.api.util.registry.content.TillingSettings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.*;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public final class BlockRegistryHelper {
    /**
     * Registers a block
     * @param id identifier of the block
     * @param block block to be registered
     */
    public static void registerBlock(Identifier id, Block block) {
        Registry.register(Registries.BLOCK, id, block);
        if (((AbstractBlockAccessor) block).getSettings() instanceof CBlockSettings settings) {
            if (settings.getFireSettings() != null)
                FlammableBlockRegistry.getDefaultInstance().add(block, settings.getFireSettings().burnChance(), settings.getFireSettings().spreadChance());
            if (settings.getTillingSettings() != null) {
                TillingSettings tillingSettings = settings.getTillingSettings();
                TillableBlockRegistry.register(block, tillingSettings.usagePredicate(), tillingSettings.tilled(), tillingSettings.droppedItem());
            }
            if (settings.getStrippedBlock() != null)
                StrippableBlockRegistry.register(block, settings.getStrippedBlock());
            if (settings.getFlattenedState() != null)
                FlattenableBlockRegistry.register(block, settings.getFlattenedState());
            if (settings.getWaxed() != null)
                OxidizableBlocksRegistry.registerWaxableBlockPair(block, settings.getWaxed());
            if (settings.getOxidized() != null)
                OxidizableBlocksRegistry.registerOxidizableBlockPair(block, settings.getOxidized());
            if (settings.getPathNodeProvider() != null) {
                LandPathNodeTypesRegistry.PathNodeTypeProvider provider = settings.getPathNodeProvider();
                if (provider instanceof LandPathNodeTypesRegistry.DynamicPathNodeTypeProvider dynamic) {
                    LandPathNodeTypesRegistry.registerDynamic(block, dynamic);
                } else if (provider instanceof LandPathNodeTypesRegistry.StaticPathNodeTypeProvider staticProvider)
                    LandPathNodeTypesRegistry.register(block, staticProvider);
            }
        }
    }

    /**
     * Registers a block into the end of {@param group}
     * @param id identifier of the block
     * @param block block to be registered
     * @param group itemgroup to insert into
     */
    public static void registerBlock(Identifier id, Block block, ItemGroup group) {
        registerBlock(id, block, new ItemRegistryHelper.ItemGroupSettings(group, null));
    }

    /**
     * Registers a block into a single {@link ItemGroup} after an item
     * @param id identifier of the block
     * @param block block to be registered
     * @param after item in the itemgroup to insert after
     * @param group itemgroup to insert into
     */
    public static void registerBlock(Identifier id, Block block, @Nullable ItemConvertible after, ItemGroup group) {
        registerBlock(id, block, new ItemRegistryHelper.ItemGroupSettings(group, after));
    }

    /**
     * Registers a block into multiple {@link ItemGroup}s
     * @param id identifier of the block
     * @param block block to be registered
     * @param groups list of itemgroups to insert into
     */
    public static void registerBlock(Identifier id, Block block, @Nullable ItemRegistryHelper.ItemGroupSettings... groups) {
        registerBlock(id, block);
        if (groups != null) {
            ItemRegistryHelper.registerItem(id, new BlockItem(block, new FabricItemSettings()), groups);
        }
    }

    /**
     * Registers all Block fields within {@param clazz}
     * @param clazz class to register fields from
     * @param namespace namespace of the mod registering blocks
     */
    public static void registerBlocks(Class<?> clazz, String namespace) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                Object fieldObj = field.get(null);
                if (fieldObj instanceof Block block) {
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, field.getName().toLowerCase()), block);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
