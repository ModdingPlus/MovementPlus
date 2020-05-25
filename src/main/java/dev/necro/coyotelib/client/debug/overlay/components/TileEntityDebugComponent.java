package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class TileEntityDebugComponent implements DebugOverlayTextComponent {

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
