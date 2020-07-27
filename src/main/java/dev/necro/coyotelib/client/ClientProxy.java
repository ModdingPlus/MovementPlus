package dev.necro.coyotelib.client;

import dev.necro.coyotelib.client.debug.overlay.CustomDebugOverlayHandler;
import dev.necro.coyotelib.client.game_rules.GameRuleRegistrationHandlerClientFix;
import dev.necro.coyotelib.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerListeners(IEventBus modEventBus){
        super.registerListeners(modEventBus);

        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(GameRuleRegistrationHandlerClientFix::init);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        CustomDebugOverlayHandler.init(event.getMinecraftSupplier().get());
    }
}
