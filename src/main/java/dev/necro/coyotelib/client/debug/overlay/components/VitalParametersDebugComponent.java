package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.FoodStats;
import net.minecraft.util.NonNullList;

public class VitalParametersDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        PlayerEntity player = debugOverlay.getPlayerEntity();
        FoodStats foodStats = player.getFoodStats();
        if(Screen.func_231173_s_()) { // @TODO func_231173_s_ -> hasShiftDown
            list.add(
                    String.format(
                            "Health: %.1f/%.1f, Food: %d(%.1f), Air: %d/%d",
                            player.getHealth(),
                            player.getMaxHealth(),
                            foodStats.getFoodLevel(),
                            foodStats.getSaturationLevel(),
                            player.getAir(),
                            player.getMaxAir()
                    )
            );
        } else {
            list.add(
                    String.format(
                            "Health: %.1f/%.1f, Food: %d(%.1f)",
                            player.getHealth(),
                            player.getMaxHealth(),
                            foodStats.getFoodLevel(),
                            foodStats.getSaturationLevel()
                    )
            );
        }
    }
}
