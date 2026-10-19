package dev.itsrealperson.tacticalworkbench.registry;

import dev.itsrealperson.tacticalworkbench.TacticalWorkbench;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final RegistrySupplier<SoundEvent> SEAT_SIT = registerSound("seat_sit");
    public static final RegistrySupplier<SoundEvent> SEAT_STAND = registerSound("seat_stand");
    public static final RegistrySupplier<SoundEvent> ATTACH_EQUIP = registerSound("attach_equip");
    public static final RegistrySupplier<SoundEvent> ATTACH_REMOVE = registerSound("attach_remove");
    public static final RegistrySupplier<SoundEvent> BOLT_CYCLE = registerSound("bolt_cycle");
    public static final RegistrySupplier<SoundEvent> SCREW_TURN = registerSound("screw_turn");
    public static final RegistrySupplier<SoundEvent> OIL_WIPE = registerSound("oil_wipe");

    private static RegistrySupplier<SoundEvent> registerSound(String name) {
        ResourceLocation id = new ResourceLocation(TacticalWorkbench.MOD_ID, name);
        return TacticalWorkbench.SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        // Loads static fields
    }
}
