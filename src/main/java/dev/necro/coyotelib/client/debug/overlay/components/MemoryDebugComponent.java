package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;

import java.util.Arrays;
import java.util.List;

public class MemoryDebugComponent implements DebugOverlayTextComponent {

    @Override
    public void addInformation(List<String> list,
                               Minecraft minecraft,
                               RayTraceResult targetedBlock,
                               RayTraceResult targetedFluid) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
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
