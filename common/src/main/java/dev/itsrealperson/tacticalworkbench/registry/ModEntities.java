package dev.itsrealperson.tacticalworkbench.registry;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.itsrealperson.tacticalworkbench.entity.WorkbenchSeatEntity;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final RegistrySupplier<EntityType<WorkbenchSeatEntity>> SEAT_ENTITY = TacticalWorkbench.ENTITY_TYPES.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "seat"),
            () -> EntityType.Builder.<WorkbenchSeatEntity>of(WorkbenchSeatEntity::new, MobCategory.MISC)
                    .sized(0.0F, 0.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("seat")
    );

    public static void register() {
        // Loads static fields
    }
}
