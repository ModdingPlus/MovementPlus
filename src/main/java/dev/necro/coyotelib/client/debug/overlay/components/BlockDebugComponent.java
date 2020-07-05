package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.state.Property;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.util.Map;
import java.util.Optional;

public class BlockDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        Optional<BlockPos> targetedBlock = debugOverlay.getTargetedBlock();
        if (targetedBlock.isPresent()) {
            BlockPos blockPos = targetedBlock.get();
            BlockState blockstate = minecraft.world.getBlockState(blockPos);

            list.add(TextFormatting.UNDERLINE + "Targeted Block");
            list.add(blockstate.getBlock().getRegistryName().toString());
            blockstate.getValues().entrySet().forEach(entry -> list.add(this.getPropertyString(entry)));
            blockstate.getBlock().getTags().forEach(t -> list.add("#" + t));
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
