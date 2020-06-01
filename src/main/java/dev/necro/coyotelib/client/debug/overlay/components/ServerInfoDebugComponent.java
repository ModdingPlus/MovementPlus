package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.NonNullList;

public class ServerInfoDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        IntegratedServer integratedserver = minecraft.getIntegratedServer();
        NetworkManager networkmanager = minecraft.getConnection().getNetworkManager();
        float f = networkmanager.getPacketsSent();
        float f1 = networkmanager.getPacketsReceived();
        String s;
        if (integratedserver != null) {
            list.add(String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", integratedserver.getTickTime(), f, f1));
        } else {
            list.add(String.format("\"%s\" server, %.0f tx, %.0f rx", minecraft.player.getServerBrand(), f, f1));
        }
    }
}
