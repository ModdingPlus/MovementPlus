package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;

import java.util.Optional;

import static net.minecraft.client.gui.overlay.DebugOverlayGui.HEIGHTMAP_NAMES;

public class HeightmapDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        BlockPos blockPos = debugOverlay.getRenderViewBlockPos();
        Optional<Chunk> clientChunk  = debugOverlay.getChunkIfAvailable();
        Optional<Chunk> serverChunk  = debugOverlay.getServerChunkIfAvailable();
        Chunk chunk;

        StringBuilder builder = new StringBuilder();
        chunk = clientChunk.orElse(null);
        builder.append("SH");

        for (Heightmap.Type heightmap$type : Heightmap.Type.values()) {
            if (heightmap$type.isUsageClient()) {
                builder.append(" ").append(HEIGHTMAP_NAMES.get(heightmap$type)).append(": ");
                if (chunk != null) {
                    builder.append(chunk.getTopBlockY(heightmap$type, blockPos.getX(), blockPos.getZ()));
                } else {
                    builder.append("??");
                }
            }
        }
        list.add(builder.toString());

        builder.setLength(0);
        chunk = serverChunk.orElse(null);
        builder.append("SH");

        for (Heightmap.Type heightmap$type : Heightmap.Type.values()) {
            if (heightmap$type.isUsageNotWorldgen()) {
                builder.append(" ").append(HEIGHTMAP_NAMES.get(heightmap$type)).append(": ");
                if (chunk != null) {
                    builder.append(chunk.getTopBlockY(heightmap$type, blockPos.getX(), blockPos.getZ()));
                } else {
                    builder.append("??");
                }
            }
        }

        list.add(builder.toString());
    }

    public String buildString(Chunk chunk, BlockPos pos, StringBuilder builder){
        return builder.toString();
    }
}
