package com.github.creoii.creolib.api.util;

import com.github.creoii.creolib.api.util.registry.CBlockSettings;
import com.github.creoii.creolib.api.util.registry.DripSettings;
import com.github.creoii.creolib.api.util.registry.FireSettings;
import com.github.creoii.creolib.api.util.registry.TillingSettings;
import com.github.creoii.creolib.mixin.block.BlockSettingsAccessor;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class BlockUtil {
    public static final Map<Block, CBlockSettings> BLOCK_SETTINGS_REPLACED = new HashMap<>();

    public static CBlockSettings getOrCreateSettings(Block block) {
        if (BLOCK_SETTINGS_REPLACED.containsKey(block)) return BLOCK_SETTINGS_REPLACED.get(block);
        else {
            CBlockSettings copy = CBlockSettings.copy(block);
            BLOCK_SETTINGS_REPLACED.put(block, copy);
            return copy;
        }
    }

    /**
     * Sets the hardness of the {@param block}
     */
    public static void setHardness(Block block, float hardness) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).hardness(hardness));
    }

    /**
     * Makes the {@param block} break instantly when mined
     */
    public static void setBreakInstantly(Block block) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).hardness(0f).resistance(0f));
    }

    /**
     * Returns the hardness of the {@param block}
     */
    public static float getHardness(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getHardness();
    }

    /**
     * Returns whether the {@param block} breaks instantly when mined
     */
    public static boolean breaksInstantly(Block block) {
        BlockSettingsAccessor settings = ((BlockSettingsAccessor) getOrCreateSettings(block));
        return settings.getHardness() == 0f && settings.getResistance() == 0f;
    }

    /**
     * Sets the resistance of the {@param block}
     */
    public static void setResistance(Block block, float resistance) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).resistance(resistance));
    }

    /**
     * Returns the resistance of the {@param block}
     */
    public static float getResistance(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getResistance();
    }

    /**
     * Sets the hardness and resistance of the {@param block}
     */
    public static void setStrength(Block block, float hardness, float resistance) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).hardness(hardness).resistance(resistance));
    }

    /**
     * Sets the slipperiness of the {@param block}
     */
    public static void setSlipperiness(Block block, float slipperiness) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).slipperiness(slipperiness));
    }

    /**
     * Returns the slipperiness of the {@param block}
     */
    public static float getSlipperiness(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getSlipperiness();
    }

    /**
     * Sets the velocity multiplier of the {@param block}
     */
    public static void setVelocityMultiplier(Block block, float multiplier) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).velocityMultiplier(multiplier));
    }

    /**
     * Returns the velocity multiplier of the {@param block}
     */
    public static float getVelocityMultiplier(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getVelocityMultiplier();
    }

    /**
     * Sets the jump velocity multiplier of the {@param block}
     */
    public static void setJumpVelocityMultiplier(Block block, float multiplier) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).jumpVelocityMultiplier(multiplier));
    }

    /**
     * Returns the jump velocity multiplier of the {@param block}
     */
    public static float getJumpVelocityMultiplier(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getJumpVelocityMultiplier();
    }

    /**
     * Sets the sound group of the {@param block}
     */
    public static void setSoundGroup(Block block, BlockSoundGroup soundGroup) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).sounds(soundGroup));
    }

    /**
     * Returns the sound group of the {@param block}
     */
    public static BlockSoundGroup getSoundGroup(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getSoundGroup();
    }

    /**
     * Sets the luminance of the {@param block}
     */
    public static void setLuminance(Block block, int luminance) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).luminance(luminance));
    }

    /**
     * Returns the luminance of the {@param block}
     */
    public static int getLuminance(BlockState state) {
        return ((BlockSettingsAccessor) getOrCreateSettings(state.getBlock())).getLuminance().applyAsInt(state);
    }

    /**
     * Returns if the {@param block} emits light
     */
    public static boolean hasLuminance(BlockState state) {
        return getLuminance(state) > 0;
    }

    /**
     * Sets the map color of the {@param block}
     */
    public static void setMapColor(Block block, MapColor color) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).mapColor(color));
    }

    /**
     * Returns the map color of the {@param block}
     */
    public static MapColor getMapColor(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).getMaterial().getColor();
    }

    /**
     * Sets the offset type of the {@param block} based on its blockstate
     */
    public static void setOffsetType(Block block, Function<BlockState, AbstractBlock.OffsetType> offsetType) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).offsetType(offsetType));
    }

    /**
     * Sets the offset type of the {@param block}
     */
    public static void setOffsetType(Block block, AbstractBlock.OffsetType offsetType) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).offsetType(state -> offsetType));
    }

    /**
     * Returns the offset type of the {@param block}
     */
    public static AbstractBlock.OffsetType getOffsetType(BlockState state) {
        return ((BlockSettingsAccessor) getOrCreateSettings(state.getBlock())).getOffsetType().apply(state);
    }

    /**
     * Makes the {@param block} non-collidable
     */
    public static void setNoCollision(Block block) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).noCollision());
    }

    /**
     * Returns whether the {@param block} is collidable
     */
    public static boolean isCollidable(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).isCollidable();
    }

    /**
     * Makes the {@param block} require a tool to drop when it is mined
     */
    public static void setToolRequired(Block block) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).requiresTool());
    }

    /**
     * Returns whether a tool is required for the {@param block} to drop when mined
     */
    public static boolean isToolRequired(Block block) {
        return ((BlockSettingsAccessor) getOrCreateSettings(block)).isToolRequired();
    }

    /**
     * Makes the {@param block} emissive
     */
    public static void setEmissive(Block block, boolean emissive) {
        setEmissive(block, (state, world, pos) -> emissive);
    }

    /**
     * Makes the {@param block} emissive based on its blockstate
     */
    public static void setEmissive(Block block, AbstractBlock.ContextPredicate emissive) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).emissiveLighting(emissive));
    }

    /**
     * Returns whether the {@param block} is emissive
     */
    public static boolean isEmissive(BlockState state, BlockView world, BlockPos pos) {
        return ((BlockSettingsAccessor) getOrCreateSettings(state.getBlock())).hasEmissiveLighting().test(state, world, pos);
    }

    /**
     * Makes the {@param block} have post-processing
     */
    public static void setPostProcessing(Block block, boolean postProcessing) {
        setPostProcessing(block, (state, world, pos) -> postProcessing);
    }

    /**
     * Makes the {@param block} have post-processing based on its blockstate
     */
    public static void setPostProcessing(Block block, AbstractBlock.ContextPredicate postProcessing) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).postProcess(postProcessing));
    }

    /**
     * Returns whether the {@param block} has post-processing
     */
    public static boolean hasPostProcessing(BlockState state, BlockView world, BlockPos pos) {
        return ((BlockSettingsAccessor) getOrCreateSettings(state.getBlock())).hasPostProcessing().test(state, world, pos);
    }

    /**
     * Makes the {@param block} suffocate entities inside of it
     */
    public static void setSuffocates(Block block, boolean suffocates) {
        setSuffocates(block, (state, world, pos) -> suffocates);
    }

    /**
     * Makes the {@param block} suffocate entities inside of it based on its blockstate
     */
    public static void setSuffocates(Block block, AbstractBlock.ContextPredicate suffocates) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).suffocates(suffocates));
    }

    /**
     * Returns whether the {@param block} suffocates entities when inside it
     */
    public static boolean doesSuffocate(BlockState state, BlockView world, BlockPos pos) {
        return ((BlockSettingsAccessor) getOrCreateSettings(state.getBlock())).suffocates().test(state, world, pos);
    }

    /**
     * Makes the {@param block} allow mob spawning on top
     */
    public static void setAllowsSpawning(Block block, boolean allows) {
        setAllowsSpawning(block, (state, world, pos, type) -> allows);
    }

    /**
     * Makes the {@param block} allow mob spawning on top based on its blockstate
     */
    public static void setAllowsSpawning(Block block, AbstractBlock.TypedContextPredicate<EntityType<?>> allows) {
        BLOCK_SETTINGS_REPLACED.replace(block, (CBlockSettings) getOrCreateSettings(block).allowsSpawning(allows));
    }

    /**
     * Returns whether the {@param block} allows mobs to spawn on top of it
     */
    public static boolean allowsSpawning(BlockState state, BlockView world, BlockPos pos, EntityType<?> entityType) {
        return ((BlockSettingsAccessor) getOrCreateSettings(state.getBlock())).allowsSpawning().test(state, world, pos, entityType);
    }

    /**
     * Sets the {@link FireSettings} of the {@param block}
     */
    public static void setFireSettings(Block block, FireSettings fireSettings) {
        BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).fireSettings(fireSettings));
    }

    /**
     * Returns the {@link FireSettings} of the {@param block}
     */
    public static FireSettings getFireSettings(Block block) {
        return getOrCreateSettings(block).getFireSettings();
    }

    /**
     * Sets the {@link DripSettings} of the {@param block}
     */
    public static void setDripSettings(Block block, DripSettings dripSettings) {
        BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).dripSettings(dripSettings));
    }

    /**
     * Returns the {@link DripSettings} of the {@param block}
     */
    public static DripSettings getDripSettings(Block block) {
        return getOrCreateSettings(block).getDripSettings();
    }

    /**
     * Sets the {@link TillingSettings} of the {@param block}
     */
    public static void setTillingSettings(Block block, TillingSettings tillingSettings) {
        BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).tillingSettings(tillingSettings));
    }

    /**
     * Returns the {@link TillingSettings} of the {@param block}
     */
    public static TillingSettings getTillingSettings(Block block) {
        return getOrCreateSettings(block).getTillingSettings();
    }

    /**
     * Sets the stripped block of the {@param block}
     */
    public static void setStrippedBlock(Block block, Block stripped) {
        BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).strippedBlock(stripped));
    }

    /**
     * Returns the stripped block of the {@param block}, if it is strippable
     */
    public static Block getStrippedBlock(Block block) {
        return getOrCreateSettings(block).getStrippedBlock();
    }

    /**
     * Sets the flattened state of the {@param block}
     */
    public static void setFlattenedState(Block block, BlockState flattened) {
        BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).flattenedState(flattened));
    }

    /**
     * Returns the flattened state of the {@param block}, if it is flattenable
     */
    public static BlockState getFlattenedState(Block block) {
        return getOrCreateSettings(block).getFlattenedState();
    }

    /**
     * Sets the path node provider of the {@param block}
     */
    public static void setPathNodeProvider(Block block, LandPathNodeTypesRegistry.PathNodeTypeProvider provider) {
        if (provider instanceof LandPathNodeTypesRegistry.StaticPathNodeTypeProvider staticProvider) {
            BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).staticPathNode(staticProvider));
        } else {
            BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).dynamicPathNode((LandPathNodeTypesRegistry.DynamicPathNodeTypeProvider) provider));
        }
    }

    /**
     * Returns the path node provider of the {@param block}
     */
    public static LandPathNodeTypesRegistry.PathNodeTypeProvider getPathNodeProvider(Block block) {
        return getOrCreateSettings(block).getPathNodeProvider();
    }

    /**
     * Sets the waxed block of the {@param block}
     */
    public static void setWaxedBlock(Block block, Block waxed) {
        BLOCK_SETTINGS_REPLACED.replace(block, getOrCreateSettings(block).waxedBlock(waxed));
    }

    /**
     * Returns the waxed block of the {@param block}, if it is waxable
     */
    public static Block getWaxedBlock(Block block) {
        return getOrCreateSettings(block).getWaxed();
    }
}