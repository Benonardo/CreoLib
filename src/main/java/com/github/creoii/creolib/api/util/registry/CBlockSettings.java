package com.github.creoii.creolib.api.util.registry;

import com.github.creoii.creolib.core.duck.AbstractBlockDuck;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import net.minecraft.block.*;
import net.minecraft.item.HoneycombItem;

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

}
