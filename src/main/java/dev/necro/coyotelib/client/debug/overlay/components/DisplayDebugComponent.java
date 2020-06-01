package dev.necro.coyotelib.client.debug.overlay.components;

import com.mojang.blaze3d.platform.PlatformDescriptors;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;

import java.util.Arrays;

public class DisplayDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        list.addAll(Arrays.asList(
                String.format("Display: %dx%d (%s)", minecraft.getMainWindow().getFramebufferWidth(), minecraft.getMainWindow().getFramebufferHeight(), PlatformDescriptors.getGlVendor()),
                PlatformDescriptors.getGlRenderer(),
                PlatformDescriptors.getGlVersion()
        ));
    }
}
