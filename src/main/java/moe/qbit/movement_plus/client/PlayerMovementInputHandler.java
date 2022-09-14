package moe.qbit.movement_plus.client;

import moe.qbit.movement_plus.MovementPlus;
import moe.qbit.movement_plus.api.client.PlayerMovementInputEvent;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MovementPlus.MODID, value = Dist.CLIENT)
public class PlayerMovementInputHandler {

    private static boolean wasSneaking = false;
    private static boolean wasJumping = false;

    @SubscribeEvent
    public static void movementInputUpdate(MovementInputUpdateEvent event){
        Player player = event.getEntity();
        Input input = event.getInput();

        if(!wasJumping && input.jumping){
            MinecraftForge.EVENT_BUS.post(new PlayerMovementInputEvent.JumpInputEvent(player));
        }
        if(!wasSneaking && input.shiftKeyDown){
            MinecraftForge.EVENT_BUS.post(new PlayerMovementInputEvent.SneakInputEvent(player));
        } else if(wasSneaking && !input.shiftKeyDown) {
            MinecraftForge.EVENT_BUS.post(new PlayerMovementInputEvent.UnsneakInputEvent(player));
        }

        wasJumping=input.jumping;
        wasSneaking=input.shiftKeyDown;
    }
}
