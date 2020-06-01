package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.lighting.WorldLightManager;

import java.util.Optional;

public class LightDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        BlockPos blockPos = debugOverlay.getRenderViewBlockPos();
        list.add(String.format("Client Light: %d (%d sky, %d block)",
                minecraft.world.getChunkProvider().getLightManager().getLightSubtracted(blockPos, 0),
                minecraft.world.getLightFor(LightType.SKY, blockPos),
                minecraft.world.getLightFor(LightType.BLOCK, blockPos)));

        World world = debugOverlay.getIntegratedServerWorld();
        Optional<Chunk> chunk = debugOverlay.getServerChunkIfAvailable();
        if (chunk.isPresent()) {
            WorldLightManager worldlightmanager = world.getChunkProvider().getLightManager();
            list.add(String.format("Server Light: (%d sky, %d block)",
                    worldlightmanager.getLightEngine(LightType.SKY).getLightFor(blockPos),
                    worldlightmanager.getLightEngine(LightType.BLOCK).getLightFor(blockPos)));
        } else {
            list.add("Server Light: (?? sky, ?? block)");
        }
    }
}
