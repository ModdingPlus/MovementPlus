package dev.necro.coyotelib.common.game_rules;

import dev.necro.coyotelib.CoyoteLib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class PreventCreeperGriefingGameRule {

    @SubscribeEvent
    public static void creeperExplode(EntityMobGriefingEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof CreeperEntity) {
            if (entity.getEntityWorld().getGameRules().getBoolean(ModGameRules.PREVENT_CREEPER_GRIEFING)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
