package com.example.sheriffhat;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Sheriff's hat. Worn in the Curios "head" slot (tag data/curios/tags/items/head.json).
 *
 * Plain:  +2 armor, +5% knockback resistance
 * Badge:  +3 armor, +1 armor toughness, +10% knockback resistance
 */
public class SheriffHatItem extends Item implements ICurioItem {
    private final boolean star;

    public SheriffHatItem(boolean star, Properties props) {
        super(props);
        this.star = star;
    }

    public boolean hasStar() {
        return star;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(uuid, "Sheriff hat armor",
                star ? 3.0 : 2.0, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Sheriff hat resolve",
                star ? 0.10 : 0.05, AttributeModifier.Operation.ADDITION));
        if (star) {
            map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Sheriff badge toughness",
                    1.0, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(star ? "tooltip.sheriffhat.sheriff_hat_star" : "tooltip.sheriffhat.sheriff_hat")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
