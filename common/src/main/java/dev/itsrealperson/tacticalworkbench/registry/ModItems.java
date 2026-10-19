package dev.itsrealperson.tacticalworkbench.registry;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.itsrealperson.tacticalworkbench.item.GunOilItem;
import dev.itsrealperson.tacticalworkbench.item.TacticalAttachmentItem;
import dev.itsrealperson.tacticalworkbench.item.TacticalRifleItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final RegistrySupplier<Item> TACTICAL_WORKBENCH_ITEM = TacticalWorkbench.ITEMS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "tactical_workbench"),
            () -> new BlockItem(ModBlocks.TACTICAL_WORKBENCH.get(), new Item.Properties())
    );

    public static final RegistrySupplier<Item> TACTICAL_RIFLE = TacticalWorkbench.ITEMS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "tactical_rifle"),
            () -> new TacticalRifleItem(new Item.Properties())
    );

    public static final RegistrySupplier<Item> REFLEX_SIGHT = TacticalWorkbench.ITEMS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "reflex_sight"),
            () -> new TacticalAttachmentItem(
                    new Item.Properties().stacksTo(1),
                    TacticalAttachmentItem.AttachmentSlot.OPTIC,
                    "tooltip.tactical_workbench.reflex_sight.desc"
            )
    );

    public static final RegistrySupplier<Item> SUPPRESSOR = TacticalWorkbench.ITEMS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "suppressor"),
            () -> new TacticalAttachmentItem(
                    new Item.Properties().stacksTo(1),
                    TacticalAttachmentItem.AttachmentSlot.BARREL,
                    "tooltip.tactical_workbench.suppressor.desc"
            )
    );

    public static final RegistrySupplier<Item> VERTICAL_GRIP = TacticalWorkbench.ITEMS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "vertical_grip"),
            () -> new TacticalAttachmentItem(
                    new Item.Properties().stacksTo(1),
                    TacticalAttachmentItem.AttachmentSlot.UNDERBARREL,
                    "tooltip.tactical_workbench.vertical_grip.desc"
            )
    );

    public static final RegistrySupplier<Item> GUN_OIL = TacticalWorkbench.ITEMS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "gun_oil"),
            () -> new GunOilItem(new Item.Properties())
    );

    public static void register() {
        // Loads static fields
    }
}
