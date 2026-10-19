package dev.itsrealperson.tacticalworkbench;

import dev.itsrealperson.tacticalworkbench.registry.ModBlockEntities;
import dev.itsrealperson.tacticalworkbench.registry.ModBlocks;
import dev.itsrealperson.tacticalworkbench.registry.ModEntities;
import dev.itsrealperson.tacticalworkbench.registry.ModItems;
import dev.itsrealperson.tacticalworkbench.registry.ModSounds;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TacticalWorkbench {
    public static final String MOD_ID = "tactical_workbench";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.ENTITY_TYPE);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);

    public static void init() {
        ModBlocks.register();
        ModBlockEntities.register();
        ModItems.register();
        ModEntities.register();
        ModSounds.register();

        BLOCKS.register();
        BLOCK_ENTITY_TYPES.register();
        ITEMS.register();
        ENTITY_TYPES.register();
        SOUND_EVENTS.register();
    }
}
