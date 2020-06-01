package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class LookingAtFluidPositionDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        Optional<BlockPos> targetedFluid = debugOverlay.getTargetedFluid();
        if (targetedFluid.isPresent()) {
            BlockPos blockPos = targetedFluid.get();
            list.add(String.format("Looking at liquid: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }
    }
}
