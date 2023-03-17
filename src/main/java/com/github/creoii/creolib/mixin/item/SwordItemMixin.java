package com.github.creoii.creolib.mixin.item;

import com.github.creoii.creolib.api.tag.CBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin {
    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void creo_lib_swordMineables(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (state.isIn(CBlockTags.SWORD_MINEABLE)) cir.setReturnValue(1.5f);
    }
}
