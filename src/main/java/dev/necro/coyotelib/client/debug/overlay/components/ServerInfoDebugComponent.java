package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;

public class ServerInfoDebugComponent implements DebugOverlayTextComponent {
    @Override
    public void addInformation(List<String> list, Minecraft minecraft, RayTraceResult targetedBlock, RayTraceResult targetedFluid) {
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

        /*BlockPos blockpos = new BlockPos(minecraft.getRenderViewEntity());
            Entity entity = this.mc.getRenderViewEntity();
            Direction direction = entity.getHorizontalFacing();

            ChunkPos chunkpos = new ChunkPos(blockpos);
            if (!Objects.equals(this.chunkPos, chunkpos)) {
                this.chunkPos = chunkpos;
                this.resetChunk();
            }

            World world = this.getWorld();
            LongSet longset = (LongSet)(world instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks() : LongSets.EMPTY_SET);
            List<String> list = Lists.newArrayList("Minecraft " + SharedConstants.getVersion().getName() + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType()) + ")", this.mc.debug, s, this.mc.worldRenderer.getDebugInfoRenders(), this.mc.worldRenderer.getDebugInfoEntities(), "P: " + this.mc.particles.getStatistics() + ". T: " + this.mc.world.getCountLoadedEntities(), this.mc.world.getProviderName());
            String s2 = this.getServerChunkStats();
            if (s2 != null) {
                list.add(s2);
            }

            list.add(DimensionType.getKey(this.mc.world.dimension.getType()).toString() + " FC: " + Integer.toString(longset.size()));
            list.add("");

                if (this.mc.world.isBlockLoaded(blockpos)) {
                    Chunk chunk = this.getChunk();
                    if (chunk.isEmpty()) {
                        list.add("Waiting for chunk...");
                    } else {



                        StringBuilder stringbuilder = new StringBuilder("CH");

                        for(Heightmap.Type heightmap$type : Heightmap.Type.values()) {
                            if (heightmap$type.isUsageClient()) {
                                stringbuilder.append(" ").append(HEIGHTMAP_NAMES.get(heightmap$type)).append(": ").append(chunk.getTopBlockY(heightmap$type, blockpos.getX(), blockpos.getZ()));
                            }
                        }

                        list.add(stringbuilder.toString());
                        stringbuilder.setLength(0);
                        stringbuilder.append("SH");

                        for(Heightmap.Type heightmap$type1 : Heightmap.Type.values()) {
                            if (heightmap$type1.isUsageNotWorldgen()) {
                                stringbuilder.append(" ").append(HEIGHTMAP_NAMES.get(heightmap$type1)).append(": ");
                                if (chunk1 != null) {
                                    stringbuilder.append(chunk1.getTopBlockY(heightmap$type1, blockpos.getX(), blockpos.getZ()));
                                } else {
                                    stringbuilder.append("??");
                                }
                            }
                        }

                        list.add(stringbuilder.toString());
                        if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
                            list.add("Biome: " + Registry.BIOME.getKey(this.mc.world.getBiome(blockpos)));
                            long l = 0L;
                            float f2 = 0.0F;
                            if (chunk1 != null) {
                                f2 = world.getCurrentMoonPhaseFactor();
                                l = chunk1.getInhabitedTime();
                            }

                            DifficultyInstance difficultyinstance = new DifficultyInstance(world.getDifficulty(), world.getDayTime(), l, f2);
                            list.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", difficultyinstance.getAdditionalDifficulty(), difficultyinstance.getClampedAdditionalDifficulty(), this.mc.world.getDayTime() / 24000L));
                        }
                    }
                } else {
                    list.add("Outside of world...");
                }

            ShaderGroup shadergroup = this.mc.gameRenderer.getShaderGroup();
            if (shadergroup != null) {
                list.add("Shader: " + shadergroup.getShaderGroupName());
            }

            if (this.rayTraceBlock.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos1 = ((BlockRayTraceResult)this.rayTraceBlock).getPos();
                list.add(String.format("Looking at block: %d %d %d", blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()));
            }

            if (this.rayTraceFluid.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos2 = ((BlockRayTraceResult)this.rayTraceFluid).getPos();
                list.add(String.format("Looking at liquid: %d %d %d", blockpos2.getX(), blockpos2.getY(), blockpos2.getZ()));
            }

            list.add(this.mc.getSoundHandler().getDebugString());
            return list;
        }*/
    }
}
