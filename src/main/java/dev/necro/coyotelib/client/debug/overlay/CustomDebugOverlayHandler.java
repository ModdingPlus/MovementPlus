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
    private static boolean _OVERLAY_KEY_PRESSED = false;
    private static boolean _OVERLAY_SHOWN = false;

    public static CustomDebugOverlayGui customDebugOverlayGui = null;

    public static final KeyBinding CUSTOM_DEBUG_OVERLAY_KEY = new KeyBinding("key."+CoyoteLib.MODID +".debug_overlay",
            KeyConflictContext.IN_GAME,
            InputMappings.getInputByName("key.keyboard.f6"),
            "key.categories.misc");

    public static void init(Minecraft minecraft){
        MinecraftForge.EVENT_BUS.addListener(CustomDebugOverlayHandler::renderDebug);

        customDebugOverlayGui = new CustomDebugOverlayGui(minecraft);
        ClientRegistry.registerKeyBinding(CUSTOM_DEBUG_OVERLAY_KEY);
    }

    public static void renderDebug(RenderGameOverlayEvent event){
        if(CUSTOM_DEBUG_OVERLAY_KEY.isKeyDown() != _OVERLAY_KEY_PRESSED){
            _OVERLAY_KEY_PRESSED = CUSTOM_DEBUG_OVERLAY_KEY.isKeyDown();
            if(_OVERLAY_KEY_PRESSED){
                _OVERLAY_SHOWN = !_OVERLAY_SHOWN;
            }
        }

        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL && _OVERLAY_SHOWN)
            customDebugOverlayGui.render();
    }
}
