package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;

public class SoundsDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        //noinspection ConstantConditions
        list.add(String.format("%s (Mood %d%%)", minecraft.getSoundHandler().getDebugString(), Math.round(minecraft.player.getDarknessAmbience() * 100.0F)));
    }
}
