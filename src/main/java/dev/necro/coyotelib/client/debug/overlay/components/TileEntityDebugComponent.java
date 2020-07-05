package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.Optional;

public class TileEntityDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        Optional<BlockPos> targetedBlock = debugOverlay.getTargetedBlock();
        if (targetedBlock.isPresent()) {
            BlockPos blockPos = targetedBlock.get();
            TileEntity tileEntity = minecraft.world.getTileEntity(blockPos);

            if(tileEntity!=null) {
                list.addAll(Arrays.asList(
                        TextFormatting.UNDERLINE + "Targeted Tile Entity",
                        tileEntity.getType().getRegistryName().toString(),
                        String.format("Tickable: %b", tileEntity instanceof ITickableTileEntity)
                ));
                if(Screen.func_231173_s_()) { // @TODO func_231173_s_ -> hasShiftDown
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
