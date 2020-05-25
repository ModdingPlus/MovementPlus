package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.state.IProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Map;

public class EntityDebugComponent implements DebugOverlayTextComponent {

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
