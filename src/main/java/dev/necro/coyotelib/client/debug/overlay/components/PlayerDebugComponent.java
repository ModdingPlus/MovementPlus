package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IProperty;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.util.Map;
import java.util.Optional;

public class PlayerDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        PlayerEntity player = debugOverlay.getPlayerEntity();
        if(Screen.hasShiftDown()) {
            list.add(
                    String.format(
                            "Name: %s (%s), UUID: %s",
                            player.getScoreboardName(),
                            player.getDisplayName().getFormattedText(),
                            player.getUniqueID().toString()
                    )
            );
        } else {
            list.add(
                    String.format(
                            "Name: %s (%s)",
                            player.getScoreboardName(),
                            player.getDisplayName().getFormattedText()
                    )
            );
        }
    }
}
