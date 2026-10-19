package dev.itsrealperson.tacticalworkbench.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TacticalRifleItem extends Item {
    public TacticalRifleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("tooltip.tactical_workbench.tactical_rifle.desc").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.tactical_workbench.tactical_rifle.mod_hint").withStyle(ChatFormatting.DARK_AQUA));
    }
}
