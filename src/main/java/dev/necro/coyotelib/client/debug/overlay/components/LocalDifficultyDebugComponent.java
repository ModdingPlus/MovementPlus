package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Locale;
import java.util.Optional;

public class LocalDifficultyDebugComponent extends DebugOverlayTextComponent {
    @Override
    public void addInformation(NonNullList<String> list, Minecraft minecraft, IDebugOverlayScreen debugOverlay) {
        World world = debugOverlay.getIntegratedServerWorld();
        BlockPos blockPos = debugOverlay.getRenderViewBlockPos();
        Optional<Chunk> chunk = debugOverlay.getServerChunkIfAvailable();

        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            long inhabitedTime = 0L;
            float moonPhaseFactor = 0.0F;
            if (chunk.isPresent()) {
                moonPhaseFactor = world.getMoonFactor();
                inhabitedTime = chunk.get().getInhabitedTime();
            }

            DifficultyInstance difficultyinstance = new DifficultyInstance(world.getDifficulty(), world.getDayTime(), inhabitedTime, moonPhaseFactor);
            list.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", difficultyinstance.getAdditionalDifficulty(), difficultyinstance.getClampedAdditionalDifficulty(), world.getDayTime() / 24000L));
        }
    }
}
