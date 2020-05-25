package dev.necro.coyotelib.client.debug.overlay.components;

import com.mojang.blaze3d.platform.PlatformDescriptors;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;

import java.util.Arrays;
import java.util.List;

public class DisplayDebugComponent implements DebugOverlayTextComponent {

    @Override
    public void addInformation(List<String> list,
                               Minecraft minecraft,
                               RayTraceResult targetedBlock,
                               RayTraceResult targetedFluidt) {
        list.addAll(Arrays.asList(
                String.format("Display: %dx%d (%s)", Minecraft.getInstance().getMainWindow().getFramebufferWidth(), Minecraft.getInstance().getMainWindow().getFramebufferHeight(), PlatformDescriptors.getGlVendor()),
                PlatformDescriptors.getGlRenderer(),
                PlatformDescriptors.getGlVersion()
        ));
    }
}
