package dev.necro.coyotelib.client.debug.overlay.components;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import dev.necro.coyotelib.common.movement.midair_jump.MidairJumpHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

public class MidairJumpDebugComponent extends DebugOverlayTextComponent {

    @Override
    public void addInformation(NonNullList<String> list,
                               Minecraft minecraft,
                               IDebugOverlayScreen debugOverlay) {
        CompoundNBT nbt = debugOverlay.getPlayerEntity().getPersistentData().getCompound(MidairJumpHandler.MIDAIR_JUMP_NBT_KEY);

        if(Screen.hasShiftDown()) {
            list.add(
                    String.format(
                            "MJJ: %d, CTT: %d, CTJ: %s",
                            nbt.getInt(MidairJumpHandler.MULTIJUMP_JUMPS_NBT_KEY),
                            nbt.getInt(MidairJumpHandler.COYOTETIME_TIME_OFF_GROUND_KEY),
                            nbt.getBoolean(MidairJumpHandler.COYOTETIME_JUMPED_KEY)
                                    ? (TextFormatting.GREEN + "true")
                                    : (TextFormatting.RED + "false")
                    )
            );
        } else {
            list.add(
                    String.format(
                            "MJJ: %d, CTT: %d",
                            nbt.getInt(MidairJumpHandler.MULTIJUMP_JUMPS_NBT_KEY),
                            nbt.getInt(MidairJumpHandler.COYOTETIME_TIME_OFF_GROUND_KEY)
                    )
            );
        }

    }
}
