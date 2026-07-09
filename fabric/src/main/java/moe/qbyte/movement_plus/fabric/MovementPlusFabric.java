package moe.qbyte.movement_plus.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

public final class MovementPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(MovementPlus.MOD_ID, ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
        NeoForgeModConfigEvents.loading(MovementPlus.MOD_ID).register(config -> {
            if (config.getSpec() == ServerConfig.SERVER_SPEC) {
                ServerConfig.cache();
            }
        });
        NeoForgeModConfigEvents.reloading(MovementPlus.MOD_ID).register(config -> {
            if (config.getSpec() == ServerConfig.SERVER_SPEC) {
                ServerConfig.cache();
                ConfigSyncPayload.syncToAll();
            }
        });

        MovementPlus.init();
    }
}
