package dev.necro.coyotelib.common.game_rules;

import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.common.game_rules.RegisterGameRuleEvent;
import net.minecraft.world.GameRules;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = CoyoteLib.MODID)
public class ModGameRules {
    public static GameRules.RuleKey<GameRules.BooleanValue> PREVENT_CREEPER_GRIEFING;
    public static GameRules.RuleKey<GameRules.BooleanValue> FORCE_ALLOW_VILLAGER_GRIEFING;
    public static GameRules.RuleKey<GameRules.BooleanValue> ARACHNOPHOBIA;

    @SubscribeEvent
    public static void registerGameRules(RegisterGameRuleEvent event){
        // @TODO: func_234903_a_ -> register
        PREVENT_CREEPER_GRIEFING = GameRules.func_234903_a_(CoyoteLib.MODID + ":preventCreeperGriefing", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
        FORCE_ALLOW_VILLAGER_GRIEFING = GameRules.func_234903_a_(CoyoteLib.MODID + ":forceAllowVillagerGriefing", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
        ARACHNOPHOBIA = GameRules.func_234903_a_(CoyoteLib.MODID + ":arachnophobia", GameRules.Category.SPAWNING, GameRules.BooleanValue.create(false));
    }
}
