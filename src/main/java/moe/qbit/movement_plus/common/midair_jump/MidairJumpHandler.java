package moe.qbit.movement_plus.common.midair_jump;

import moe.qbit.movement_plus.MovementPlus;
import moe.qbit.movement_plus.api.common.midair_jump.MidairJumpEvent;
import moe.qbit.movement_plus.api.client.PlayerMovementInputEvent;
import moe.qbit.movement_plus.common.ModSounds;
import moe.qbit.movement_plus.common.attributes.ModAttributes;
import moe.qbit.movement_plus.common.config.ServerConfig;
import moe.qbit.movement_plus.common.network.PacketHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MovementPlus.MODID)
public class MidairJumpHandler {

    public static final String MIDAIR_JUMP_NBT_KEY = MovementPlus.MODID + ":midair_jump";
    public static final String MULTIJUMP_JUMPS_NBT_KEY = "multijump.jumps";
    public static final String COYOTETIME_TIME_OFF_GROUND_KEY = "coyotetime.timeOffGround";
    public static final String COYOTETIME_JUMPED_KEY = "coyotetime.jumped";

    private static CompoundTag getNBT(Entity player){
        CompoundTag persistentData = player.getPersistentData();
        if(!persistentData.contains(MIDAIR_JUMP_NBT_KEY, Tag.TAG_COMPOUND))
            persistentData.put(MIDAIR_JUMP_NBT_KEY, new CompoundTag());
        return persistentData.getCompound(MIDAIR_JUMP_NBT_KEY);
    }

    /** Detect when the player is on the ground and reset jumps accordingly */
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;

        CompoundTag nbt = getNBT(player);

        if (player.isAlive()
                && ((player.isOnGround() && !player.isFallFlying())
                    || player.isSwimming()
                    || player.isVisuallySwimming()
                    || player.isVisuallyCrawling()
                    || player.isUnderWater()
                    || player.isFallFlying()
                    || player.isSleeping())){
            nbt.putInt(COYOTETIME_TIME_OFF_GROUND_KEY, 0);
            nbt.putBoolean(COYOTETIME_JUMPED_KEY, false);
        } else if(event.phase == TickEvent.Phase.END) {
            nbt.putInt(COYOTETIME_TIME_OFF_GROUND_KEY, nbt.getInt(COYOTETIME_TIME_OFF_GROUND_KEY) + 1);
        }

        if (!player.isAlive()) return;
        if ((player.isOnGround() && !player.isFallFlying())
                || player.isSwimming()
                || player.isVisuallySwimming()
                || player.isVisuallyCrawling()
                || player.isUnderWater()
                || player.isFallFlying()
                || player.isSleeping()){
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
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        CompoundTag nbt = getNBT(player);
        nbt.putBoolean(COYOTETIME_JUMPED_KEY, true);
    }

    /** when the player presses the jump on the client side */
    @SubscribeEvent
    public static void playerJumpInput(PlayerMovementInputEvent.JumpInputEvent event){
        attemptPlayerJump(event.getEntity(), LogicalSide.CLIENT);
    }

    /** when the player presses the jump on the client side */
    public static void playerJumpPacket(Player player){
        attemptPlayerJump(player, LogicalSide.SERVER);
    }

    public static void attemptPlayerJump(Player player, LogicalSide side){
        if (!player.isAlive()) return;
        if (!(player.isSwimming()
                || player.isOnGround()
                || player.isVisuallySwimming()
                || player.isVisuallyCrawling()
                || player.isUnderWater()
                || player.isFallFlying()
                || player.isSleeping())) {
            CompoundTag nbt = getNBT(player);

            if (player.getDeltaMovement().y < 0 && !nbt.getBoolean(COYOTETIME_JUMPED_KEY)){
                int coyoteTime = (int) Math.floor(player.getAttributeValue(ModAttributes.COYOTE_TIME.get()) + ServerConfig.coyoteTime);

                if (coyoteTime > 0) {
                    int timeOffGround = nbt.getInt(COYOTETIME_TIME_OFF_GROUND_KEY);
                    if (timeOffGround <= coyoteTime) {

                        if (!MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.CoyoteTime.Pre(player))) {
                            performJump(player, side);

                            MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.CoyoteTime.Post(player));
                            return;
                        }

                    }
                }
            }

            int multiJumps = (int) Math.floor(player.getAttributeValue(ModAttributes.MULTI_JUMPS.get()) + ServerConfig.multiJumps);

            if (multiJumps > 0) {
                int usedJumps = nbt.getInt(MULTIJUMP_JUMPS_NBT_KEY);

                if (usedJumps < multiJumps) {
                    if (!MinecraftForge.EVENT_BUS.post(new MidairJumpEvent.MultiJump.Pre(player))) {
                        nbt.putInt(MULTIJUMP_JUMPS_NBT_KEY, usedJumps + 1);
                        performJump(player, side);

                        MidairJumpEvent.MultiJump.Post postMultiJumpEvent = new MidairJumpEvent.MultiJump.Post(player);
                        MinecraftForge.EVENT_BUS.post(postMultiJumpEvent);
                        if (postMultiJumpEvent.shouldPlayEffects()) playMultiJumpEffects(player);
                        return;
                    }
                }
            }

            MidairJumpEvent.SpecialJump specialJumpEvent = new MidairJumpEvent.SpecialJump(player);
            if (!MinecraftForge.EVENT_BUS.post(specialJumpEvent) && specialJumpEvent.canJump()){
                MidairJumpHandler.performJump(player, side);
                if(specialJumpEvent.hasCallback()) specialJumpEvent.getCallback().accept(player);
            }
        }
    }

    public static void performJump(Player player, LogicalSide side){
        if(side==LogicalSide.CLIENT) PacketHandler.INSTANCE.sendToServer(new MidairJumpPacket());
        player.jumpFromGround();
    }

    public static void playMultiJumpEffects(Player player){
        Level level = player.level;

        player.playSound(ModSounds.MIDAIR_JUMP.get(), .6f, 1.8f);

        if(!level.isClientSide() && level instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 0);
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void playerFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player)) return;
        CompoundTag nbt = getNBT(entity);
        int jumps = nbt.getInt(MULTIJUMP_JUMPS_NBT_KEY);
        if (jumps<=0) return;
        event.setDistance((float) Math.max(0, event.getDistance() - ServerConfig.jumpHeightBoost / 1.5d - 1d));
    }
}
