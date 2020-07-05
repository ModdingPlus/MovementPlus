package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.Property;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class FluidDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        Optional<BlockPos> targetedFluid = debugOverlay.getTargetedFluid();
        if (targetedFluid.isPresent()) {
            BlockPos blockPos = targetedFluid.get();
            FluidState fluidState = minecraft.world.getFluidState(blockPos);

            list.addAll(Arrays.asList(
                    TextFormatting.UNDERLINE + "Targeted Fluid",
                    fluidState.getFluid().getRegistryName().toString()
            ));
            fluidState.getValues().entrySet().forEach(entry -> list.add(this.getPropertyString(entry)));
            fluidState.getFluid().getTags().forEach(t -> list.add("#" + t));
        }
    }

    private String getPropertyString(Map.Entry<Property<?>, Comparable<?>> entry) {
        Property<?> property = entry.getKey();
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
