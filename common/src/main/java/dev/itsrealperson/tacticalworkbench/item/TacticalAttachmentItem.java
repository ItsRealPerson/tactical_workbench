package dev.itsrealperson.tacticalworkbench.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TacticalAttachmentItem extends Item {
    public enum AttachmentSlot {
        OPTIC("slot.tactical_workbench.optic", ChatFormatting.AQUA),
        BARREL("slot.tactical_workbench.barrel", ChatFormatting.GOLD),
        UNDERBARREL("slot.tactical_workbench.underbarrel", ChatFormatting.GREEN);

        private final String translationKey;
        private final ChatFormatting formatting;

        AttachmentSlot(String translationKey, ChatFormatting formatting) {
            this.translationKey = translationKey;
            this.formatting = formatting;
        }

        public String getTranslationKey() {
            return translationKey;
        }

        public ChatFormatting getFormatting() {
            return formatting;
        }
    }

    private final AttachmentSlot slot;
    private final String descriptionKey;

    public TacticalAttachmentItem(Properties properties, AttachmentSlot slot, String descriptionKey) {
        super(properties);
        this.slot = slot;
        this.descriptionKey = descriptionKey;
    }

    public AttachmentSlot getSlot() {
        return slot;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("tooltip.tactical_workbench.slot_type")
                .append(": ")
                .append(Component.translatable(slot.getTranslationKey()).withStyle(slot.getFormatting())));

        if (descriptionKey != null) {
            tooltipComponents.add(Component.translatable(descriptionKey).withStyle(ChatFormatting.GRAY));
        }
    }
}
