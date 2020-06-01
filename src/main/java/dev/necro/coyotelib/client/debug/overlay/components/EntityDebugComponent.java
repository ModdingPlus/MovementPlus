package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.state.IProperty;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;

import java.util.Map;

public class EntityDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        Entity entity = minecraft.pointedEntity;
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
