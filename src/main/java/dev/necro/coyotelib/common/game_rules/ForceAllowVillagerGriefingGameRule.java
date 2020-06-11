package dev.necro.coyotelib.common.game_rules;

import dev.necro.coyotelib.CoyoteLib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class ForceAllowVillagerGriefingGameRule {

    @SubscribeEvent
    public static void villagerGriefing(EntityMobGriefingEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof VillagerEntity) {
            if (entity.getEntityWorld().getGameRules().getBoolean(ModGameRules.FORCE_ALLOW_VILLAGER_GRIEFING)) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
