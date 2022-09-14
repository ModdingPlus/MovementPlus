package moe.qbit.movement_plus.common;

import moe.qbit.movement_plus.MovementPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = MovementPlus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MovementPlus.MODID);
    public static final RegistryObject<SoundEvent> MIDAIR_JUMP = SOUND_EVENTS.register("midair_jump", () -> new SoundEvent(new ResourceLocation(MovementPlus.MODID, "midair_jump")));

    public static void register(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }
}