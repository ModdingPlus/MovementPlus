package dev.necro.coyotelib.common.gamerules;

import net.minecraft.world.level.GameRules;

public class ModGameRules {
    public static GameRules.Key<GameRules.IntegerValue> COYOTE_TIME;
    public static GameRules.Key<GameRules.IntegerValue> MULTI_JUMPS;

    public static void register(){
        COYOTE_TIME = GameRules.register("coyoteTime", GameRules.Category.PLAYER, GameRules.IntegerValue.create(3));
        MULTI_JUMPS = GameRules.register("multiJumps", GameRules.Category.PLAYER, GameRules.IntegerValue.create(0));
    }
}
