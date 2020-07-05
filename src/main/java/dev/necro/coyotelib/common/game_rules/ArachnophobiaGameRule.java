package dev.necro.coyotelib.common.game_rules;

import dev.necro.coyotelib.CoyoteLib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class ArachnophobiaGameRule {

    @SubscribeEvent
    public static void entitySpawn(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof SpiderEntity) {
            if (entity.getEntityWorld().getGameRules().getBoolean(ModGameRules.ARACHNOPHOBIA)) {
                event.setCanceled(true);
            }
        }
    }
}
