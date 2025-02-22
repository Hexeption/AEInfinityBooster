package uk.co.hexeption.aeinfinitybooster.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;

import java.util.List;

/**
 * DimensionCard
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 11:19 am
 */
public class DimensionCard extends Item {
    public DimensionCard() {
        super(new Item.Properties().durability(0));
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Style style = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY).withItalic(true);
        tooltipComponents.add(Component.translatable("item.aeinfinitybooster.dimension_card.tooltip").withStyle(style));
    }
}
