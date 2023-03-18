package com.github.creoii.creolib.api.util.registry;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemUsageContext;

import java.util.function.Predicate;

public record TillingSettings(Predicate<ItemUsageContext> usagePredicate, BlockState tilled, ItemConvertible droppedItem) { }
