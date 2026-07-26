package moe.qbyte.movement_plus.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.config.ServerConfig;
import moe.qbyte.movement_plus.registry.ModAttributes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.config.ModConfig;

public final class MovementPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigRegistry.INSTANCE.register(MovementPlus.MOD_ID, ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
        ModConfigEvents.loading(MovementPlus.MOD_ID).register(config -> {
            if (config.getSpec() == ServerConfig.SERVER_SPEC) {
                ServerConfig.cache();
            }
        });
        ModConfigEvents.reloading(MovementPlus.MOD_ID).register(config -> {
            if (config.getSpec() == ServerConfig.SERVER_SPEC) {
                ServerConfig.cache();
                ConfigSyncPayload.syncToAll();
            }
        });

        MovementPlus.init();

        // Replaces the player's default attribute supplier with one that includes ours;
        // must run after MovementPlus.init() has registered the attributes.
        FabricDefaultAttributeRegistry.register(EntityType.PLAYER, Player.createAttributes()
                .add(ModAttributes.MULTI_JUMPS.holder())
                .add(ModAttributes.COYOTE_TIME.holder())
                .add(ModAttributes.SWIM_SPEED.holder()));
    }
}
