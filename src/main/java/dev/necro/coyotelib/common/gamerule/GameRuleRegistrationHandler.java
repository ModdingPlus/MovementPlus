package dev.necro.coyotelib.common.gamerule;

import com.mojang.brigadier.CommandDispatcher;
import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.common.movement.midair_jump.RegisterGameRuleEvent;
import net.minecraft.command.CommandSource;
import net.minecraft.command.impl.GameRuleCommand;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class GameRuleRegistrationHandler {

    @SubscribeEvent
    public static void init(final FMLServerAboutToStartEvent event){
        CommandDispatcher<CommandSource> dispatcher = event.getServer().getCommandManager().getDispatcher();

        MinecraftForge.EVENT_BUS.post(new RegisterGameRuleEvent());

        GameRuleCommand.register(dispatcher);
    }
}
