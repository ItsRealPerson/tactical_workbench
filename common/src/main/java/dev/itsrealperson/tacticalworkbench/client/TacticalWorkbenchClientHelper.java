package dev.itsrealperson.tacticalworkbench.client;

import dev.itsrealperson.tacticalworkbench.block.entity.TacticalWorkbenchBlockEntity;
import dev.itsrealperson.tacticalworkbench.client.screen.WorkbenchScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class TacticalWorkbenchClientHelper {
    public static void openWorkbenchScreen(TacticalWorkbenchBlockEntity be) {
        Minecraft.getInstance().setScreen(new WorkbenchScreen(be));
    }
}
