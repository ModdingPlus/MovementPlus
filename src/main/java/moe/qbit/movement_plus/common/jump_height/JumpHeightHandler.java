package moe.qbit.movement_plus.common.jump_height;

import moe.qbit.movement_plus.MovementPlus;
import moe.qbit.movement_plus.common.config.ServerConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MovementPlus.MODID)
public class JumpHeightHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void playerJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!(entity instanceof Player)) return;
        if (ServerConfig.jumpHeightBoost == 0.0d) return;
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x, Math.max(0, motion.y + (0.1d * ServerConfig.jumpHeightBoost)), motion.z);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void playerFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!(entity instanceof Player)) return;
        event.setDistance((float) Math.max(0, event.getDistance() - ServerConfig.jumpHeightBoost));
    }
}
