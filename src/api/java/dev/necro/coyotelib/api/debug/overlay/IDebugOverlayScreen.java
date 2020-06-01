package dev.necro.coyotelib.api.debug.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Optional;

public interface IDebugOverlayScreen {
    Entity getRenderViewEntity();
    BlockPos getRenderViewBlockPos();
    World getWorld();
    World getIntegratedServerWorld();
    Minecraft getMinecraft();
    RayTraceResult getTargetedBlockRayTrace();
    RayTraceResult getTargetedFluidRayTrace();
    Optional<BlockPos> getTargetedBlock();
    Optional<BlockPos> getTargetedFluid();
    Optional<Chunk> getChunkIfAvailable();
    Optional<Chunk> getServerChunkIfAvailable();
}
