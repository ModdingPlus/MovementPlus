package dev.necro.coyotelib.client.debug.overlay;

import com.mojang.blaze3d.platform.PlatformDescriptors;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface DebugOverlayComponent {
    void addInformation(List<String> list,
                        Minecraft minecraft,
                        RayTraceResult targetedBlock,
                        RayTraceResult targetedFluid);

    class Memory implements DebugOverlayComponent{

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

    class CPU implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            list.addAll(Arrays.asList(
                    String.format("CPU: %s", PlatformDescriptors.getCpuInfo())
            ));
        }
    }

    class Display implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluidt) {
            list.addAll(Arrays.asList(
                    String.format("Display: %dx%d (%s)", Minecraft.getInstance().getMainWindow().getFramebufferWidth(), Minecraft.getInstance().getMainWindow().getFramebufferHeight(), PlatformDescriptors.getGlVendor()),
                    PlatformDescriptors.getGlRenderer(),
                    PlatformDescriptors.getGlVersion()
            ));
        }
    }

    class Block implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            if (targetedBlock.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockRayTraceResult)targetedBlock).getPos();
                BlockState blockstate = minecraft.world.getBlockState(blockpos);

                list.addAll(Arrays.asList(
                        TextFormatting.UNDERLINE + "Targeted Block",
                        blockstate.getBlock().getRegistryName().toString()
                ));
                blockstate.getValues().entrySet().forEach(entry -> list.add(this.getPropertyString(entry)));
                blockstate.getBlock().getTags().forEach(t -> list.add("#" + t));
            }
        }

        private String getPropertyString(Map.Entry<IProperty<?>, Comparable<?>> entry) {
            IProperty<?> property = entry.getKey();
            Comparable<?> value = entry.getValue();
            String s = Util.getValueName(property, value);
            if (Boolean.TRUE.equals(value)) {
                s = TextFormatting.GREEN + s;
            } else if (Boolean.FALSE.equals(value)) {
                s = TextFormatting.RED + s;
            }
            return property.getName() + ": " + s;
        }
    }

    class TileEntity implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            if (targetedBlock.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockRayTraceResult)targetedBlock).getPos();
                net.minecraft.tileentity.TileEntity tileEntity = minecraft.world.getTileEntity(blockpos);

                if(tileEntity!=null) {
                    list.addAll(Arrays.asList(
                            TextFormatting.UNDERLINE + "Targeted Tile Entity",
                            tileEntity.getType().getRegistryName().toString(),
                            String.format("Tickable: %b", tileEntity instanceof ITickableTileEntity)
                    ));
                    if(Screen.hasShiftDown()){
                        CompoundNBT nbt = tileEntity.serializeNBT().copy();
                        nbt.remove("x");
                        nbt.remove("y");
                        nbt.remove("z");
                        nbt.remove("id");
                        list.add(nbt.toString());
                    }
                }
            }
        }
    }

    class Fluid implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            if (targetedFluid.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockRayTraceResult)targetedFluid).getPos();
                IFluidState fluidState = minecraft.world.getFluidState(blockpos);

                list.addAll(Arrays.asList(
                        TextFormatting.UNDERLINE + "Targeted Fluid",
                        fluidState.getFluid().getRegistryName().toString()
                ));
                fluidState.getValues().entrySet().forEach(entry -> list.add(this.getPropertyString(entry)));
                fluidState.getFluid().getTags().forEach(t -> list.add("#" + t));
            }
        }

        private String getPropertyString(Map.Entry<IProperty<?>, Comparable<?>> entry) {
            IProperty<?> property = entry.getKey();
            Comparable<?> value = entry.getValue();
            String s = Util.getValueName(property, value);
            if (Boolean.TRUE.equals(value)) {
                s = TextFormatting.GREEN + s;
            } else if (Boolean.FALSE.equals(value)) {
                s = TextFormatting.RED + s;
            }
            return property.getName() + ": " + s;
        }
    }

    class Entity implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            net.minecraft.entity.Entity entity = minecraft.pointedEntity;
            if (entity != null) {
                list.add(TextFormatting.UNDERLINE + "Targeted Entity");
                list.add(entity.getType().getRegistryName().toString());
                entity.getType().getTags().forEach(t -> list.add("#" + t));
                if(Screen.hasShiftDown()) {
                    list.add(entity.getPersistentData().toString());
                }
            }
        }


        private String getPropertyString(Map.Entry<IProperty<?>, Comparable<?>> entry) {
            IProperty<?> property = entry.getKey();
            Comparable<?> value = entry.getValue();
            String s = Util.getValueName(property, value);
            if (Boolean.TRUE.equals(value)) {
                s = TextFormatting.GREEN + s;
            } else if (Boolean.FALSE.equals(value)) {
                s = TextFormatting.RED + s;
            }
            return property.getName() + ": " + s;
        }
    }


    class Sounds implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            Map<ISound, ChannelManager.Entry> playingSounds = minecraft.getSoundHandler().sndManager.playingSoundsChannel;

            if(!playingSounds.isEmpty()) {
                list.add(TextFormatting.UNDERLINE + "Playing Sounds");
                list.addAll(playingSounds.keySet().stream()
                        .map(ISound::getSoundLocation).map(ResourceLocation::toString)
                        .collect(Collectors.toList()));
            }
        }
    }

    class ItemStack implements DebugOverlayComponent{

        @Override
        public void addInformation(List<String> list,
                                   Minecraft minecraft,
                                   RayTraceResult targetedBlock,
                                   RayTraceResult targetedFluid) {
            PlayerEntity player = minecraft.player;

            net.minecraft.item.ItemStack stack = player.getHeldItemMainhand();
            if(stack.isEmpty())
                stack = player.getHeldItemOffhand();

            if (!stack.isEmpty()) {
                list.add(TextFormatting.UNDERLINE + "Held Item");
                list.add(stack.getItem().getRegistryName().toString());
                if(stack.isDamageable()) {
                    list.add(String.format("Damage: %d / %d", stack.getDamage(), stack.getMaxDamage()));
                }
                stack.getItem().getTags().forEach(t -> list.add("#" + t));
                if(Screen.hasShiftDown() && stack.hasTag())
                    list.add(stack.getTag().toString());
            }
        }
    }
}
