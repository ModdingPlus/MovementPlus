package moe.qbit.movement_plus.common.step_height;

import moe.qbit.movement_plus.MovementPlus;
import moe.qbit.movement_plus.common.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MovementPlus.MODID)
public class StepHeightHandler{

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void playerTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;

        if (event.phase == TickEvent.Phase.START) {
            event.player.maxUpStep = player.isCrouching() ? (float) ServerConfig.stepHeightSneaking : (float) ServerConfig.stepHeight;
        }

    }
}
