package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.IProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FluidDebugComponent implements DebugOverlayTextComponent {

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
