package dev.necro.coyotelib.client.debug.overlay.components;

import com.google.common.collect.Lists;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;

public class MinecraftVersionDebugComponent implements DebugOverlayTextComponent {
    @Override
    public void addInformation(List<String> list, Minecraft minecraft, RayTraceResult targetedBlock, RayTraceResult targetedFluid) {
        list.add(String.format("Minecraft %s (%s/%s)", SharedConstants.getVersion().getName(), minecraft.getVersion(), ClientBrandRetriever.getClientModName()));
        /*return Lists.newArrayList(
                minecraft.debug,
                s,
                minecraft.worldRenderer.getDebugInfoRenders(),
                minecraft.worldRenderer.getDebugInfoEntities(),
                "P: " + minecraft.particles.getStatistics() + ". T: " + minecraft.world.getCountLoadedEntities(),
                minecraft.world.getProviderName(),
                "",
                String.format("Chunk-relative: %d %d %d", blockpos.getX() & 15, blockpos.getY() & 15, blockpos.getZ() & 15));*/
    }
}
