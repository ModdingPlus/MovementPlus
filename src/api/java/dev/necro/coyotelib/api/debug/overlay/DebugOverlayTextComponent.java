package dev.necro.coyotelib.api.debug.overlay;

import jdk.internal.jline.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class DebugOverlayTextComponent extends ForgeRegistryEntry<DebugOverlayTextComponent> {
    /**
     * This field will be null if CoyoteLib isn't loaded.
     */
    @Nullable
    public static IForgeRegistry<DebugOverlayTextComponent> REGISTRY;

    public abstract void addInformation(NonNullList<String> list,
                                        Minecraft minecraft,
                                        IDebugOverlayScreen debugOverlay);

    public ITextComponent getDisplayName(){
        return new TranslationTextComponent(Util.makeTranslationKey("debug_component", this.getRegistryName()));
    }
}
