package com.github.creoii.creolib.api.util.registry;

import com.github.creoii.creolib.api.util.registry.content.DripSettings;
import com.github.creoii.creolib.api.util.registry.content.FireSettings;
import com.github.creoii.creolib.api.util.registry.content.TillingSettings;
import com.github.creoii.creolib.core.duck.AbstractBlockDuck;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.HoneycombItem;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class CBlockSettings extends FabricBlockSettings {
    private FireSettings fireSettings;
    private DripSettings dripSettings;
    private TillingSettings tillingSettings;
    private Block strippedBlock;
    private BlockState flattenedState;
    private LandPathNodeTypesRegistry.PathNodeTypeProvider pathNodeProvider;
    private Block waxed;
    private Block oxidized;

    public CBlockSettings(Material material, MapColor color) {
        super(material, color);
    }

    public CBlockSettings(AbstractBlock.Settings settings) {
        super(settings);
    }

    public CBlockSettings(FabricBlockSettings settings) {
        super(settings);
    }

    public static CBlockSettings copy(Block block) {
        CBlockSettings settings = new CBlockSettings(block.getDefaultState().getMaterial(), block.getDefaultMapColor());
        settings.strength(block.getHardness(), block.getBlastResistance());
        if (block.hasRandomTicks(block.getDefaultState())) settings.ticksRandomly();
        settings.sounds(block.getSoundGroup(block.getDefaultState()));
        settings.slipperiness(block.getSlipperiness());
        settings.velocityMultiplier(block.getVelocityMultiplier());
        settings.jumpVelocityMultiplier(block.getVelocityMultiplier());
        settings.fireSettings(((AbstractBlockDuck) block).getFireSettings());
        settings.dripSettings(((AbstractBlockDuck) block).getDripSettings());
        settings.tillingSettings(((AbstractBlockDuck) block).getTillingSettings());
        settings.strippedBlock(AxeItemAccessor.getStrippedBlocks().get(block));
        settings.flattenedState(ShovelItemAccessor.getPathStates().get(block));
        LandPathNodeTypesRegistry.PathNodeTypeProvider provider = LandPathNodeTypesRegistry.getPathNodeTypeProvider(block);
        if (provider instanceof LandPathNodeTypesRegistry.StaticPathNodeTypeProvider staticProvider) {
            settings.staticPathNode(staticProvider);
        } else {
            settings.dynamicPathNode((LandPathNodeTypesRegistry.DynamicPathNodeTypeProvider) provider);
        }
        settings.waxedBlock(HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().get(block));
        settings.oxidizedBlock(Oxidizable.OXIDATION_LEVEL_INCREASES.get().get(block));
        return settings;
    }

    public static CBlockSettings of(Material material) {
        return of(material, material.getColor());
    }

    public static CBlockSettings of(Material material, MapColor color) {
        return new CBlockSettings(material, color);
    }

    public static CBlockSettings of(Material material, DyeColor color) {
        return new CBlockSettings(material, color.getMapColor());
    }

    public FireSettings getFireSettings() {
        return fireSettings;
    }

    public DripSettings getDripSettings() {
        return dripSettings;
    }

    public TillingSettings getTillingSettings() {
        return tillingSettings;
    }

    public Block getStrippedBlock() {
        return strippedBlock;
    }

    public BlockState getFlattenedState() {
        return flattenedState;
    }

    public LandPathNodeTypesRegistry.PathNodeTypeProvider getPathNodeProvider() {
        return pathNodeProvider;
    }

    public Block getWaxed() {
        return waxed;
    }

    public Block getOxidized() {
        return oxidized;
    }

    public CBlockSettings fireSettings(FireSettings fireSettings) {
        this.fireSettings = fireSettings;
        return this;
    }

    public CBlockSettings dripSettings(DripSettings dripSettings) {
        this.dripSettings = dripSettings;
        return this;
    }

    public CBlockSettings tillingSettings(TillingSettings tillingSettings) {
        this.tillingSettings = tillingSettings;
        return this;
    }

    public CBlockSettings strippedBlock(Block strippedBlock) {
        this.strippedBlock = strippedBlock;
        return this;
    }

    public CBlockSettings flattenedState(BlockState flattened) {
        this.flattenedState = flattened;
        return this;
    }

    public CBlockSettings staticPathNode(LandPathNodeTypesRegistry.StaticPathNodeTypeProvider provider) {
        this.pathNodeProvider = provider;
        return this;
    }

    public CBlockSettings dynamicPathNode(LandPathNodeTypesRegistry.DynamicPathNodeTypeProvider provider) {
        this.pathNodeProvider = provider;
        return this;
    }

    public CBlockSettings waxedBlock(Block waxed) {
        this.waxed = waxed;
        return this;
    }

    public CBlockSettings oxidizedBlock(Block oxidized) {
        this.oxidized = oxidized;
        return this;
    }

    @Override
    public CBlockSettings noCollision() {
        super.noCollision();
        return this;
    }

    @Override
    public CBlockSettings nonOpaque() {
        super.nonOpaque();
        return this;
    }

    @Override
    public CBlockSettings slipperiness(float value) {
        super.slipperiness(value);
        return this;
    }

    @Override
    public CBlockSettings velocityMultiplier(float velocityMultiplier) {
        super.velocityMultiplier(velocityMultiplier);
        return this;
    }

    @Override
    public CBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
        super.jumpVelocityMultiplier(jumpVelocityMultiplier);
        return this;
    }

    @Override
    public CBlockSettings sounds(BlockSoundGroup group) {
        super.sounds(group);
        return this;
    }

    @Override
    public CBlockSettings luminance(ToIntFunction<BlockState> luminanceFunction) {
        super.luminance(luminanceFunction);
        return this;
    }

    @Override
    public CBlockSettings strength(float hardness, float resistance) {
        super.strength(hardness, resistance);
        return this;
    }

    @Override
    public CBlockSettings breakInstantly() {
        super.breakInstantly();
        return this;
    }

    public CBlockSettings strength(float strength) {
        super.strength(strength);
        return this;
    }

    @Override
    public CBlockSettings ticksRandomly() {
        super.ticksRandomly();
        return this;
    }

    @Override
    public CBlockSettings dynamicBounds() {
        super.dynamicBounds();
        return this;
    }

    @Override
    public CBlockSettings dropsNothing() {
        super.dropsNothing();
        return this;
    }

    @Override
    public CBlockSettings dropsLike(Block block) {
        super.dropsLike(block);
        return this;
    }

    @Override
    public CBlockSettings air() {
        super.air();
        return this;
    }

    @Override
    public CBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
        super.allowsSpawning(predicate);
        return this;
    }

    @Override
    public CBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
        super.solidBlock(predicate);
        return this;
    }

    @Override
    public CBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
        super.suffocates(predicate);
        return this;
    }

    @Override
    public CBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
        super.blockVision(predicate);
        return this;
    }

    @Override
    public CBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
        super.postProcess(predicate);
        return this;
    }

    @Override
    public CBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
        super.emissiveLighting(predicate);
        return this;
    }

    /**
     * Make the block require tool to drop and slows down mining speed if the incorrect tool is used.
     */
    @Override
    public CBlockSettings requiresTool() {
        super.requiresTool();
        return this;
    }

    @Override
    public CBlockSettings mapColor(MapColor color) {
        super.mapColor(color);
        return this;
    }

    @Override
    public CBlockSettings hardness(float hardness) {
        super.hardness(hardness);
        return this;
    }

    @Override
    public CBlockSettings resistance(float resistance) {
        super.resistance(resistance);
        return this;
    }

    @Override
    public CBlockSettings offset(AbstractBlock.OffsetType offsetType) {
        super.offset(offsetType);
        return this;
    }

    @Override
    public CBlockSettings noBlockBreakParticles() {
        super.noBlockBreakParticles();
        return this;
    }

    @Override
    public CBlockSettings requires(FeatureFlag... features) {
        super.requires(features);
        return this;
    }

    public CBlockSettings luminance(int luminance) {
        super.luminance(luminance);
        return this;
    }

    public CBlockSettings drops(Identifier dropTableId) {
        super.drops(dropTableId);
        return this;
    }
}
