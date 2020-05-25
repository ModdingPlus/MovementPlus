package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundsDebugComponent implements DebugOverlayTextComponent {

    @Override
    public void addInformation(List<String> list,
                               Minecraft minecraft,
                               RayTraceResult targetedBlock,
                               RayTraceResult targetedFluid) {
        Map<ISound, ChannelManager.Entry> playingSounds = minecraft.getSoundHandler().sndManager.playingSoundsChannel;

        if(!playingSounds.isEmpty()) {
            list.add(TextFormatting.UNDERLINE + "Playing Sounds");
            list.addAll(playingSounds.keySet().stream()
                    .map(ISound::getSoundLocation).map(ResourceLocation::toString)
                    .collect(Collectors.toList()));
        }
    }
}
