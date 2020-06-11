package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;
import java.util.stream.Collectors;

public class PotionEffectsDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        PlayerEntity player = debugOverlay.getPlayerEntity();

        Collection<EffectInstance> effects = player.getActivePotionEffects();

        if(!effects.isEmpty()) {
            list.add(TextFormatting.UNDERLINE + "Potion Effects");
            list.addAll(effects.stream()
                    .map(e -> String.format(
                                "%s %d (%d)",
                                e.getPotion().getRegistryName(),
                                e.getAmplifier(),
                                e.getDuration()))
                    .collect(Collectors.toList()));
        }
    }
}
