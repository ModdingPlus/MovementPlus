package dev.necro.coyotelib.common.movement.midair_jump;

import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.client.movement.PlayerMovementInputEvent;
import dev.necro.coyotelib.api.common.movement.midair_jump.MidairJumpEvent;
import dev.necro.coyotelib.common.network.PacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class MidairJumpHandler {

    public static final String MIDAIR_JUMP_NBT_KEY = CoyoteLib.MODID + ":midair_jump";
    public static final String MULTIJUMP_JUMPS_NBT_KEY = "multijump.jumps";
    public static final String COYOTETIME_TIME_OFF_GROUND_KEY = "coyotetime.timeOffGround";
    public static final String COYOTETIME_JUMPED_KEY = "coyotetime.jumped";

    private static CompoundNBT getNBT(PlayerEntity player){
        CompoundNBT persistentData = player.getPersistentData();
        if(!persistentData.contains(MIDAIR_JUMP_NBT_KEY, Constants.NBT.TAG_COMPOUND))
            persistentData.put(MIDAIR_JUMP_NBT_KEY, new CompoundNBT());
        return persistentData.getCompound(MIDAIR_JUMP_NBT_KEY);
    }

    /** Detect when the player is on the ground and reset jumps accordingly */
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;

        CompoundNBT nbt = getNBT(player);

        if (player.isAlive() &&  ((player.onGround && !player.isElytraFlying()) || player.isSleeping())){
            nbt.putInt(COYOTETIME_TIME_OFF_GROUND_KEY, 0);
            nbt.putBoolean(COYOTETIME_JUMPED_KEY, false);
        } else {
            if(event.phase == TickEvent.Phase.END)
                nbt.putInt(COYOTETIME_TIME_OFF_GROUND_KEY, nbt.getInt(COYOTETIME_TIME_OFF_GROUND_KEY) + 1);
        }

        if (player.isAlive() &&
                ((player.onGround && !player.isElytraFlying())
                        || player.isSwimming()
                        || player.isActualySwimming()
                        || player.isSleeping())){
            int jumps = nbt.getInt(MULTIJUMP_JUMPS_NBT_KEY);

            MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.Reset(player));

            if (jumps != 0){
                nbt.putInt(MULTIJUMP_JUMPS_NBT_KEY, 0);
            }
        }
    }

    /** Detect when the player jumps to make sure he can't use the coyote jump until they land */
    @SubscribeEvent
    public static void livingJump(LivingEvent.LivingJumpEvent event){
        if(!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity)event.getEntity();

        CompoundNBT nbt = getNBT(player);
        nbt.putBoolean(COYOTETIME_JUMPED_KEY, true);
    }

    /** when the player presses the jump on the client side */
    @SubscribeEvent
    public static void playerJumpInput(PlayerMovementInputEvent.JumpInputEvent event){
        attemptPlayerJump(event.getPlayer(), LogicalSide.CLIENT);
    }

    /** when the player presses the jump on the client side */
    public static void playerJumpPacket(PlayerEntity player){
        attemptPlayerJump(player, LogicalSide.SERVER);
    }

    public static void attemptPlayerJump(PlayerEntity player, LogicalSide side){
        if (player.isAlive() &&
                player.jumpTicks != 10 &&
                !player.onGround &&
                !player.isElytraFlying() &&
                !player.isSwimming() &&
                !player.isActualySwimming() &&
                !player.isSleeping()) {
            CompoundNBT nbt = getNBT(player);

            if (player.getMotion().y < 0 && !nbt.getBoolean(COYOTETIME_JUMPED_KEY)){
                int timeOffGround = nbt.getInt(COYOTETIME_TIME_OFF_GROUND_KEY);

                MidairJumpEvent.SetCoyoteTime event = new MidairJumpEvent.SetCoyoteTime(player, 0);
                MinecraftForge.EVENT_BUS.post(event);
                if (timeOffGround <= event.getCoyoteTime()) {

                    if(!MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.CoyoteTimeJump.Pre(player))){
                        performJump(player, side);
                        MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.CoyoteTimeJump.Post(player));
                        return;
                    }
                }
            }

            MidairJumpEvent.SpecialJump specialJumpEvent = new MidairJumpEvent.SpecialJump(player);
            if (!MinecraftForge.EVENT_BUS.post(specialJumpEvent) && specialJumpEvent.canJump()){
                MidairJumpHandler.performJump(player, side);
                if(specialJumpEvent.hasCallback())
                    specialJumpEvent.getCallback().accept(player);
                return;
            }

            MidairJumpEvent.SetMultiJumpCount setMultiJumpCountEvent = new MidairJumpEvent.SetMultiJumpCount(player);
            int jumps = nbt.getInt(MULTIJUMP_JUMPS_NBT_KEY);

            if (!MinecraftForge.EVENT_BUS.post(setMultiJumpCountEvent) && jumps < setMultiJumpCountEvent.getJumps()) {
                if (!MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.MultiJump.Pre(player))){
                    MidairJumpHandler.performJump(player, side);
                    nbt.putInt(MULTIJUMP_JUMPS_NBT_KEY, jumps + 1);
                    MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.MultiJump.Post(player));
                }
            }
        }
    }

    public static void performJump(PlayerEntity player, LogicalSide side){
        if(side==LogicalSide.CLIENT) PacketHandler.INSTANCE.sendToServer(new MidairJumpPacket());
        player.jump();
    }
}
