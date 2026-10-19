package dev.itsrealperson.tacticalworkbench.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GunOilItem extends Item {
    public GunOilItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("tooltip.tactical_workbench.gun_oil.desc").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.tactical_workbench.gun_oil.effect").withStyle(ChatFormatting.YELLOW));
    }
}
