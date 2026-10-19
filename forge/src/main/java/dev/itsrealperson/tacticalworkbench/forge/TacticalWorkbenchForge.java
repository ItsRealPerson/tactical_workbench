package dev.itsrealperson.tacticalworkbench.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.itsrealperson.tacticalworkbench.client.render.TacticalWorkbenchRenderer;
import dev.itsrealperson.tacticalworkbench.registry.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TacticalWorkbench.MOD_ID)
public class TacticalWorkbenchForge {
    public TacticalWorkbenchForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(TacticalWorkbench.MOD_ID, modEventBus);
        TacticalWorkbench.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(TacticalWorkbenchForge::onRegisterRenderers);
        });
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.TACTICAL_WORKBENCH_BE.get(), TacticalWorkbenchRenderer::new);
    }
}
