package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;

import java.util.Arrays;

public class MemoryDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        list.addAll(Arrays.asList(
                String.format("Java: %s %dbit",
                        System.getProperty("java.version"),
                        minecraft.isJava64bit() ? 64 : 32
                ),
                String.format("Mem: % 2d%% %03d/%03dMiB", usedMemory * 100L / maxMemory, bytesToMiB(usedMemory), bytesToMiB(maxMemory) ),
                String.format("Allocated: % 2d%% %03dMiB", totalMemory * 100L / maxMemory, totalMemory / 1024L / 1024L)
        ));
    }

    private long bytesToMiB(long bytes){
        return bytes / 1024 / 1024;
    }
}
