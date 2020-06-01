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

        /*BlockPos blockpos = new BlockPos(minecraft.getRenderViewEntity());

            ChunkPos chunkpos = new ChunkPos(blockpos);
            if (!Objects.equals(this.chunkPos, chunkpos)) {
                this.chunkPos = chunkpos;
                this.resetChunk();
            }

            World world = this.getWorld();

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
                    }
                } else {
                    list.add("Outside of world...");
                }



            return list;
        }*/
    }
}
