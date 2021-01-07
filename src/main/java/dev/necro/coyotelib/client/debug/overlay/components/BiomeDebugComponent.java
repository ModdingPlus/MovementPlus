package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomeDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        BlockPos blockPos = debugOverlay.getRenderViewBlockPos();
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            Biome biome = minecraft.world.getBiome(blockPos);
            if(Screen.hasShiftDown())
                list.add(String.format("Biome: %s (%s)", minecraft.world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(biome), biome.getCategory().getName()));
            else
                list.add(String.format("Biome: %s", minecraft.world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(biome)));
        }
    }
}
