package moe.qbit.movement_plus.common;

import moe.qbit.movement_plus.common.attributes.ModAttributes;
import moe.qbit.movement_plus.common.config.ServerConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {
    protected IEventBus modEventBus;

    public CommonProxy(){
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
        registerListeners(modEventBus);
    }
    
    public void registerListeners(IEventBus modEventBus){
        modEventBus.addListener(this::setup);
        ModAttributes.register(modEventBus);
    }

    public void setup(final FMLCommonSetupEvent event) {}
}
