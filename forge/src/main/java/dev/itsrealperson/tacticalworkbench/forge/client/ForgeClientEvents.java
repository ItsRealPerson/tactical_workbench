package dev.itsrealperson.tacticalworkbench.forge.client;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.itsrealperson.tacticalworkbench.client.camera.DiegeticCameraController;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TacticalWorkbench.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if (DiegeticCameraController.isActive() || DiegeticCameraController.getProgress() > 0.0f) {
            DiegeticCameraController.applyCameraTransform(event.getCamera(), (float) event.getPartialTick());
            event.setYaw(DiegeticCameraController.getCurrentYaw((float) event.getPartialTick()));
            event.setPitch(DiegeticCameraController.getCurrentPitch((float) event.getPartialTick()));
        }
    }
}
