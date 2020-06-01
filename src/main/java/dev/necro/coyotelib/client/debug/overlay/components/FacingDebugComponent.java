package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import java.util.Locale;

public class FacingDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        Entity entity = debugOverlay.getRenderViewEntity();
        Direction direction = entity.getHorizontalFacing();
        String s1;
        switch(direction) {
            case NORTH:
                s1 = "Towards negative Z";
                break;
            case SOUTH:
                s1 = "Towards positive Z";
                break;
            case WEST:
                s1 = "Towards negative X";
                break;
            case EAST:
                s1 = "Towards positive X";
                break;
            default:
                s1 = "Invalid";
        }
        list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, s1, MathHelper.wrapDegrees(entity.rotationYaw), MathHelper.wrapDegrees(entity.rotationPitch)));
    }
}
