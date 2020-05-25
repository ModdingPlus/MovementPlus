package dev.necro.coyotelib.client.debug.overlay;

import dev.necro.coyotelib.CoyoteLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CustomDebugOverlayHandler {
    public static CustomDebugOverlayGui customDebugOverlayGui = null;
    public static final KeyBinding CUSTOM_DEBUG_OVERLAY_KEY = new KeyBinding("key."+CoyoteLib.MODID +".debug_overlay",
            KeyConflictContext.IN_GAME,
            InputMappings.getInputByName("key.keyboard.f6"),
            "key.categories.misc");

    public static void init(Minecraft minecraft){
        customDebugOverlayGui = new CustomDebugOverlayGui(minecraft);
        ClientRegistry.registerKeyBinding(CUSTOM_DEBUG_OVERLAY_KEY);

        MinecraftForge.EVENT_BUS.addListener(CustomDebugOverlayHandler::renderDebug);
    }

    public static void renderDebug(RenderGameOverlayEvent event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL && CUSTOM_DEBUG_OVERLAY_KEY.isKeyDown())
            new CustomDebugOverlayGui(Minecraft.getInstance()).render();
    }
}
