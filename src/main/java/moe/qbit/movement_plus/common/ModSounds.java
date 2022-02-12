package moe.qbit.movement_plus.common;

import moe.qbit.movement_plus.MovementPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MovementPlus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {
    public static final SoundEvent JUMP = createSoundEvent(new ResourceLocation(MovementPlus.MODID, "entity.player.jump"));

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(JUMP);
    }

    private static SoundEvent createSoundEvent(ResourceLocation name){
        return new SoundEvent(name).setRegistryName(name);
    }
}