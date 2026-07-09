package moe.qbyte.movement_plus.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import moe.qbyte.movement_plus.MovementPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(MovementPlus.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> MIDAIR_JUMP = SOUND_EVENTS.register("midair_jump",
            () -> SoundEvent.createVariableRangeEvent(MovementPlus.id("midair_jump")));

    private ModSounds() {}

    public static void register() {
        SOUND_EVENTS.register();
    }
}
