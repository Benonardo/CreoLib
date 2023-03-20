package com.github.creoii.creolib.api.util.registry;

import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public final class RegistrySets {
    /**
     * Registers most blocks required in a wood set.
     *      Does not register signs, leaves, saplings, or boats.
     *      This is only for generic wood types, any special properties require normal registration.
     *
     * @param namespace - The namespace of your mod, used for ids.
     * @param name - The name of the wood type, used for ids.
     * @param barkColor - The MapColor of the wood set bark.
     * @param woodColor - The MapColor of the wood set wood.
     * @param after - Item to place after in item groups
     */
    public static WoodSet createWoodSet(String namespace, String name, MapColor barkColor, MapColor woodColor, @Nullable ItemConvertible after, @Nullable ItemConvertible logAfter, @Nullable ItemConvertible signAfter) {
        return createWoodSet(namespace, name, barkColor, woodColor, after, logAfter, signAfter, true);
    }

    public static WoodSet createWoodSet(String namespace, String name, MapColor barkColor, MapColor woodColor, @Nullable ItemConvertible after, @Nullable ItemConvertible logAfter, @Nullable ItemConvertible signAfter, boolean includeLogs) {
        BlockSetType blockSetType = BlockSetType.register(new BlockSetType(name));
        WoodType woodType = new WoodType(name, blockSetType);
        Block strippedLog = null;
        Block log = null;
        Block strippedWood = null;
        Block wood = null;
        if (includeLogs) {
            strippedLog = new PillarBlock(CBlockSettings.of(Material.WOOD).strength(2f).sounds(BlockSoundGroup.WOOD).mapColor(woodColor));
            log = new PillarBlock(CBlockSettings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? woodColor : barkColor).strength(2f).sounds(BlockSoundGroup.WOOD));
            strippedWood = new PillarBlock(CBlockSettings.copy(strippedLog));
            wood = new PillarBlock(CBlockSettings.copy(strippedLog).mapColor(barkColor));
        }
        Block planks = new Block(CBlockSettings.of(Material.WOOD).strength(2f, 3f).sounds(BlockSoundGroup.WOOD).mapColor(woodColor));
        Block slab = new SlabBlock(CBlockSettings.copy(planks));
        Block stairs = new StairsBlock(planks.getDefaultState(), CBlockSettings.copy(planks));
        Block fence = new FenceBlock(CBlockSettings.copy(planks));
        Block fenceGate = new FenceGateBlock(CBlockSettings.copy(planks), woodType);
        Block button = new ButtonBlock(CBlockSettings.copy(planks), blockSetType, 30, true);
        Block pressurePlate = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, CBlockSettings.copy(planks), blockSetType);
        Block door = new DoorBlock(CBlockSettings.copy(planks).strength(3f).sounds(BlockSoundGroup.WOOD).nonOpaque(), blockSetType);
        Block trapdoor = new TrapdoorBlock(CBlockSettings.copy(planks).strength(3f).nonOpaque().allowsSpawning((state, world, pos, type) -> false), blockSetType);
        Block sign = new SignBlock(CBlockSettings.copy(Blocks.OAK_SIGN), woodType);
        Block wallSign = new WallSignBlock(CBlockSettings.copy(Blocks.OAK_WALL_SIGN).mapColor(woodColor).dropsLike(sign), woodType);
        return new WoodSet(namespace, name, woodType, after, logAfter, signAfter, log, strippedLog, wood, strippedWood, planks, stairs, slab, fence, fenceGate, button, pressurePlate, door, trapdoor, sign, wallSign);
    }

    public record WoodSet(String namespace, String name, WoodType woodType, @Nullable ItemConvertible after, @Nullable ItemConvertible logAfter, @Nullable ItemConvertible signAfter, @Nullable Block log, @Nullable Block strippedLog, @Nullable Block wood, @Nullable Block strippedWood, Block planks, Block stairs, Block slab, Block fence, Block fenceGate, Block button, Block pressurePlate, Block door, Block trapdoor, Block sign, Block wallSign) {
        public WoodSet register() {
            if (after != null) {
                if (log != null)
                    if (logAfter != null) {
                        BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_log"), log, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, after), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, logAfter));
                    } else {
                        BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_log"), log, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, after), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, null));
                    }
                if (strippedLog != null)
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, "stripped_" + name + "_log"), strippedLog, log, ItemGroups.BUILDING_BLOCKS);
                if (wood != null)
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_wood"), wood, strippedLog, ItemGroups.BUILDING_BLOCKS);
                if (strippedWood != null)
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, "stripped_" + name + "_wood"), strippedWood, wood, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_planks"), planks, strippedWood, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_stairs"), stairs, planks, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_slab"), slab, stairs, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_fence"), fence, slab, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_fence_gate"), fenceGate, fence, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_door"), door, fenceGate, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_trapdoor"), trapdoor, door, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_pressure_plate"), pressurePlate, trapdoor, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_button"), button, pressurePlate, ItemGroups.BUILDING_BLOCKS);
            } else {
                if (log != null) {
                    if (logAfter != null) {
                        BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_log"), log, new ItemRegistryHelper.ItemGroupSettings(ItemGroups.BUILDING_BLOCKS, null), new ItemRegistryHelper.ItemGroupSettings(ItemGroups.NATURAL, logAfter));
                    } else {
                        BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_log"), log, ItemGroups.BUILDING_BLOCKS);
                    }
                }
                if (strippedLog != null)
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, "stripped_" + name + "_log"), strippedLog, ItemGroups.BUILDING_BLOCKS);
                if (wood != null)
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_wood"), wood, ItemGroups.BUILDING_BLOCKS);
                if (strippedWood != null)
                    BlockRegistryHelper.registerBlock(new Identifier(namespace, "stripped_" + name + "_wood"), strippedWood, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_planks"), planks, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_stairs"), stairs, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_slab"), slab, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_fence"), fence, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_fence_gate"), fenceGate, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_door"), door, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_trapdoor"), trapdoor, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_pressure_plate"), pressurePlate, ItemGroups.BUILDING_BLOCKS);
                BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_button"), button, ItemGroups.BUILDING_BLOCKS);
            }
            BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_sign"), sign, signAfter, ItemGroups.FUNCTIONAL);
            BlockRegistryHelper.registerBlock(new Identifier(namespace, name + "_wall_sign"), wallSign, signAfter, ItemGroups.FUNCTIONAL);
            return this;
        }
    }

    public record StoneSet(String namespace, String name, @Nullable ItemConvertible after, @Nullable ItemConvertible stoneAfter, @Nullable Block stone, Block stairs, Block slab, Block wall, @Nullable Block cobbled, @Nullable Block cobbledStairs, @Nullable Block cobbledSlab, @Nullable Block cobbledWall, @Nullable Block bricks, @Nullable Block brickStairs, @Nullable Block brickSlab, @Nullable Block brickWall, @Nullable Block tiles, @Nullable Block tileStairs, @Nullable Block tileSlab, @Nullable Block tileWall, @Nullable Block polished, @Nullable Block chiseled, @Nullable Block pillar) {
        public StoneSet register() {
            if (after != null) {
            } else {
            }
            return this;
        }
    }
}
