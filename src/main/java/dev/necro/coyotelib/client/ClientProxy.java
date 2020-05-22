package dev.necro.coyotelib.client;

import dev.necro.coyotelib.client.debug.overlay.CustomDebugOverlayGui;
import dev.necro.coyotelib.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {
    public static CustomDebugOverlayGui customDebugOverlayGui = null;

    @Override
    public void registerListeners(IEventBus modEventBus){
        super.registerListeners(modEventBus);

        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::renderDebug);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        customDebugOverlayGui = new CustomDebugOverlayGui(event.getMinecraftSupplier().get());
    }

    public void renderDebug(RenderGameOverlayEvent event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL)
            new CustomDebugOverlayGui(Minecraft.getInstance()).render();
    }
}
