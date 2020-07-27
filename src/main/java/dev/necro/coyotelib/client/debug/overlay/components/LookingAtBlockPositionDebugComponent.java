package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

@Deprecated
public class LookingAtBlockPositionDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        Optional<BlockPos> targetedBlock = debugOverlay.getTargetedBlock();
        if (targetedBlock.isPresent()) {
            BlockPos blockPos = targetedBlock.get();
            list.add(String.format("Looking at block: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }
    }
}
