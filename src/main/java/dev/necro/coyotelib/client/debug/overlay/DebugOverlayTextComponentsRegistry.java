package dev.necro.coyotelib.client.debug.overlay;

import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CoyoteLib.MODID)
public class DebugOverlayTextComponentsRegistry {
    public static IForgeRegistry<DebugOverlayTextComponent> INSTANCE;

    @SubscribeEvent
    public static void createRegistry(RegistryEvent.NewRegistry event) {
        DebugOverlayTextComponent.REGISTRY =
        DebugOverlayTextComponentsRegistry.INSTANCE =
                new RegistryBuilder<DebugOverlayTextComponent>()
                        .setType(DebugOverlayTextComponent.class)
                        .setName(new ResourceLocation(CoyoteLib.MODID, "debug_components"))
                        .create();
    }
}
