package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.NonNullList;

public class ShaderDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        ShaderGroup shadergroup = minecraft.gameRenderer.getShaderGroup();
        if (shadergroup != null) {
            list.add("Shader: " + shadergroup.getShaderGroupName());
        }
    }
}
