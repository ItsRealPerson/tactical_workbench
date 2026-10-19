package dev.itsrealperson.tacticalworkbench.registry;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.itsrealperson.tacticalworkbench.block.TacticalWorkbenchBlock;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final RegistrySupplier<Block> TACTICAL_WORKBENCH = TacticalWorkbench.BLOCKS.register(
            new ResourceLocation(TacticalWorkbench.MOD_ID, "tactical_workbench"),
            () -> new TacticalWorkbenchBlock(BlockBehaviour.Properties.of()
                    .strength(2.5f, 3.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion())
    );

    public static void register() {
        // Loads static fields
    }
}
