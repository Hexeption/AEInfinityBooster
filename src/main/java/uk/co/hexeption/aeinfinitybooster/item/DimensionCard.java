package uk.co.hexeption.aeinfinitybooster.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

//? if >=1.21.5 {
import java.util.function.Consumer;
import net.minecraft.world.item.component.TooltipDisplay;
//?} else {
/*import java.util.List;
*///?}
//? if <1.20.5 {
/*import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
*///?}

/**
 * DimensionCard
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 22/12/2021 - 11:19 am
 */
public class DimensionCard extends Item {
    public DimensionCard(Properties properties) {
        super(properties.durability(0));
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    //? if >=1.21.5 {
    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(tooltip());
    }
    //?} else if >=1.20.5 {
    /*@Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(tooltip());
    }
    *///?} else {
    /*@Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(tooltip());
    }
    *///?}

    private static Component tooltip() {
        Style style = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY).withItalic(true);
        return Component.translatable("item.aeinfinitybooster.dimension_card.tooltip").withStyle(style);
    }
}
