package dev.itsrealperson.tacticalworkbench.fabric.client;

import dev.itsrealperson.tacticalworkbench.client.render.TacticalWorkbenchRenderer;
import dev.itsrealperson.tacticalworkbench.registry.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

@Environment(EnvType.CLIENT)
public class TacticalWorkbenchFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(ModBlockEntities.TACTICAL_WORKBENCH_BE.get(), TacticalWorkbenchRenderer::new);
    }
}
