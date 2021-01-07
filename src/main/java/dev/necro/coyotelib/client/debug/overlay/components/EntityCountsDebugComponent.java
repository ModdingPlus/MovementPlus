package dev.necro.coyotelib.client.debug.overlay.components;

import com.mojang.blaze3d.platform.PlatformDescriptors;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.NonNullList;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityCountsDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        ServerWorld serverWorld = (ServerWorld)debugOverlay.getIntegratedServerWorld();
        if (serverWorld != null) {
            WorldEntitySpawner.EntityDensityManager entityDensityManager = serverWorld.getChunkProvider().func_241101_k_();
            if (entityDensityManager != null) {
                Object2IntMap<EntityClassification> object2IntMap = entityDensityManager.func_234995_b_();
                int sc = entityDensityManager.func_234988_a_();
                list.add(
                        String.format("SC: %d, %s",
                                sc,
                                Stream.of(EntityClassification.values()).map((entityClassification) -> {
                                    return String.format("%C: %d", entityClassification.getName().charAt(0), object2IntMap.getInt(entityClassification));
                                }).collect(Collectors.joining(", "))
                        )
                );
            } else {
                list.add("SC: N/A");
            }
        }
    }
}
