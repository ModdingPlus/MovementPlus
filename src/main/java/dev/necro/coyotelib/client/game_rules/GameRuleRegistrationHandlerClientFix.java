package dev.necro.coyotelib.client.game_rules;

import dev.necro.coyotelib.api.common.game_rules.RegisterGameRuleEvent;
import dev.necro.coyotelib.common.game_rules.GameRuleRegistrationHandler;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GameRuleRegistrationHandlerClientFix {
    @SubscribeEvent
    public static void init(final GuiOpenEvent event){
        if(GameRuleRegistrationHandler.registered || !(event.getGui() instanceof MainMenuScreen)) return;
        MinecraftForge.EVENT_BUS.post(new RegisterGameRuleEvent());
        GameRuleRegistrationHandler.registered = true;
    }
}
