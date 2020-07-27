package dev.necro.coyotelib.common.game_rules;

import com.mojang.brigadier.CommandDispatcher;
import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.common.game_rules.RegisterGameRuleEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.impl.GameRuleCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class GameRuleRegistrationHandler {
    public static boolean registered = false;

    @SubscribeEvent
    public static void init(final FMLServerAboutToStartEvent event){
        if(!GameRuleRegistrationHandler.registered) {
            MinecraftForge.EVENT_BUS.post(new RegisterGameRuleEvent());
            GameRuleRegistrationHandler.registered = true;
        }

        CommandDispatcher<CommandSource> dispatcher = event.getServer().getCommandManager().getDispatcher();
        GameRuleCommand.register(dispatcher);
    }
}
