package moe.qbyte.movement_plus.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.config.ServerConfig;
import moe.qbyte.movement_plus.registry.ModAttributes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityTypes;
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

        // Extends the player's default supplier instead of replacing it, so attributes other
        // mods add survive; fires at registry freeze, after every mod has initialized.
        FabricDefaultAttributeRegistry.MODIFY.register(context -> context.modify(EntityTypes.PLAYER,
                (type, builder) -> ModAttributes.all().forEach(entry -> builder.add(entry.holder()))));
    }
}
