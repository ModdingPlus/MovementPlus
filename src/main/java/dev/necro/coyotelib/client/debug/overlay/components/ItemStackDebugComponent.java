package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

public class ItemStackDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
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
