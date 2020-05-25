package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;
import java.util.Locale;

public class PositionDebugComponent implements DebugOverlayTextComponent {
    @Override
    public void addInformation(List<String> list, Minecraft minecraft, RayTraceResult targetedBlock, RayTraceResult targetedFluid) {
        Entity entity = minecraft.getRenderViewEntity();
        BlockPos blockPos = entity.getPosition();
        list.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", entity.getPosX(), entity.getPosY(), entity.getPosZ()));
        list.add(String.format("Block: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        list.add(String.format("Chunk: %d %d %d in %d %d %d", blockPos.getX() & 15, blockPos.getY() & 15, blockPos.getZ() & 15, blockPos.getX() >> 4, blockPos.getY() >> 4, blockPos.getZ() >> 4));
    }
}
