package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Map;
import java.util.stream.Collectors;

public class PlayingSoundsDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        Map<ISound, ChannelManager.Entry> playingSounds = minecraft.getSoundHandler().sndManager.playingSoundsChannel;

        if(!playingSounds.isEmpty()) {
            list.add(TextFormatting.UNDERLINE + "Playing Sounds");
            list.addAll(playingSounds.keySet().stream()
                    .map(ISound::getSoundLocation).map(ResourceLocation::toString)
                    .collect(Collectors.toList()));
        }
    }
}
