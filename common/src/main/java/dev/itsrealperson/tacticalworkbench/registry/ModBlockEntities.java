package dev.itsrealperson.tacticalworkbench.registry;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.itsrealperson.tacticalworkbench.block.entity.TacticalWorkbenchBlockEntity;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final RegistrySupplier<BlockEntityType<TacticalWorkbenchBlockEntity>> TACTICAL_WORKBENCH_BE =
            TacticalWorkbench.BLOCK_ENTITY_TYPES.register(
                    new ResourceLocation(TacticalWorkbench.MOD_ID, "tactical_workbench"),
                    () -> BlockEntityType.Builder.of(
                            TacticalWorkbenchBlockEntity::new,
                            ModBlocks.TACTICAL_WORKBENCH.get()
                    ).build(null)
            );

    public static void register() {
        // Loads static fields
    }
}
