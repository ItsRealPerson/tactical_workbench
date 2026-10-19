package dev.itsrealperson.tacticalworkbench.fabric;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import net.fabricmc.api.ModInitializer;

public class TacticalWorkbenchFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TacticalWorkbench.init();
    }
}
