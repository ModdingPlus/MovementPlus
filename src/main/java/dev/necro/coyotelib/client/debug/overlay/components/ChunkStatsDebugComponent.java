package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class ChunkStatsDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        list.add(minecraft.world.getProviderName());

        String s2 = this.getServerChunkStats(minecraft);
        if (s2 != null) {
            list.add(s2);
        }
    }

    @Nullable
    private String getServerChunkStats(Minecraft minecraft) {
        IntegratedServer integratedserver = minecraft.getIntegratedServer();
        if (integratedserver != null) {
            ServerWorld serverworld = integratedserver.getWorld(minecraft.world.getDimensionKey());
            if (serverworld != null) {
                return serverworld.getProviderName();
            }
        }

        return null;
    }
}
