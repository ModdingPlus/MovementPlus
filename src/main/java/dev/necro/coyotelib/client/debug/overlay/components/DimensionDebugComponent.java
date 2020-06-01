package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DimensionDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        World world = debugOverlay.getIntegratedServerWorld();
        int force_loaded = world instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks().size() : 0;
        list.add(String.format("%s FC: %d",world.dimension.getType().getRegistryName(), force_loaded));
    }
}
