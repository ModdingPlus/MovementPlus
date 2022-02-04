package dev.necro.coyotelib.common;

import dev.necro.coyotelib.common.network.PacketHandler;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {
    protected IEventBus modEventBus;

    public CommonProxy(){
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        registerListeners(modEventBus);
    }
    
    public void registerListeners(IEventBus modEventBus){
        modEventBus.addListener(this::setup);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            GameRules.register("coyoteTime", GameRules.Category.PLAYER, GameRules.IntegerValue.create(3));
            GameRules.register("multiJumps", GameRules.Category.PLAYER, GameRules.IntegerValue.create(0));
        });
        ExperienceOrb
    }
}
