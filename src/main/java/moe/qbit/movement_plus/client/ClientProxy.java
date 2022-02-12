package moe.qbit.movement_plus.client;

import moe.qbit.movement_plus.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerListeners(IEventBus modEventBus){
        super.registerListeners(modEventBus);

        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(PlayerMovementInputHandler::movementInputUpdate);
    }

    public void clientSetup(final FMLClientSetupEvent event) {}
}
