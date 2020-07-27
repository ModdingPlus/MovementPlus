package dev.necro.coyotelib.client.movement;

import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.client.movement.PlayerMovementInputEvent;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MovementInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CoyoteLib.MODID, value = Dist.CLIENT)
public class PlayerMovementInputHandler {

    private static boolean wasSneaking = false;
    private static boolean wasJumping = false;

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
        if (event.side == LogicalSide.SERVER || !(event.player instanceof ClientPlayerEntity)) return;

        ClientPlayerEntity player = (ClientPlayerEntity)event.player;

        if(player==null||event.phase!=TickEvent.Phase.START) return;

        MovementInput input = player.movementInput;

        if(!wasJumping && input.jump){
            MinecraftForge.EVENT_BUS.post(new PlayerMovementInputEvent.JumpInputEvent(player));
        }
        if(!wasSneaking && input.sneaking){
            MinecraftForge.EVENT_BUS.post(new PlayerMovementInputEvent.SneakInputEvent(player));
        } else if(wasSneaking && !input.sneaking) {
            MinecraftForge.EVENT_BUS.post(new PlayerMovementInputEvent.UnsneakInputEvent(player));
        }

        wasJumping=input.jump;
        wasSneaking=input.sneaking;
    }
}
