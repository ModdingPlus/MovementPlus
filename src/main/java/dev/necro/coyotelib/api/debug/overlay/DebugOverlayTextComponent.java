package dev.necro.coyotelib.api.debug.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface DebugOverlayTextComponent {
    void addInformation(List<String> list,
                        Minecraft minecraft,
                        RayTraceResult targetedBlock,
                        RayTraceResult targetedFluid);


}
