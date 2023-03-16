package com.github.creoii.creolib.api.util.datagen;

import com.github.creoii.creolib.api.util.StringUtil;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.stat.StatType;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class DataGenUtil {
    /**
     * Auto-generates English translations based on the field names declared in @param clazz
     *
     * @param builder translation builder associated with your mod
     * @param clazz class to generate translations from
     */
    public static void generateTranslations(FabricLanguageProvider.TranslationBuilder builder, Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            try {
                Object obj = field.get(null);
                if (obj instanceof Block block) {
                    builder.add(block, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof Item item) {
                    builder.add(item, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof EntityType<?> entityType) {
                    builder.add(entityType, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof ItemGroup group) {
                    builder.add(group, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof Enchantment enchantment) {
                    builder.add(enchantment, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof EntityAttribute attribute) {
                    builder.add(attribute, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof StatType<?> statType) {
                    builder.add(statType, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                } else if (obj instanceof StatusEffect statusEffect) {
                    builder.add(statusEffect, StringUtils.remove(StringUtil.capitalizeAfterAll(field.getName(), '_'), '_'));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
