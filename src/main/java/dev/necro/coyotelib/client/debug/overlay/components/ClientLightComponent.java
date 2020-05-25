package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class ClientLightComponent implements DebugOverlayTextComponent {
    @Override
    public void addInformation(List<String> list, Minecraft minecraft, RayTraceResult targetedBlock, RayTraceResult targetedFluid) {
        Entity entity = minecraft.getRenderViewEntity();
        BlockPos blockPos = entity.getPosition();
        int i = minecraft.world.getChunkProvider().getLightManager().getLightSubtracted(blockPos, 0);
        int j = minecraft.world.getLightFor(LightType.SKY, blockPos);
        int k = minecraft.world.getLightFor(LightType.BLOCK, blockPos);
        list.add(String.format("Client Light: %d ( %d sky, %d block)", i, j ,k));
    }
}
