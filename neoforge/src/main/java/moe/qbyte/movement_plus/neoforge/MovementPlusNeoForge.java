package moe.qbyte.movement_plus.neoforge;

import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.MovementPlusClient;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.config.ServerConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(MovementPlus.MOD_ID)
public final class MovementPlusNeoForge {
    public MovementPlusNeoForge(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
        modBus.addListener(this::onConfigLoading);
        modBus.addListener(this::onConfigReloading);
        modBus.addListener(this::onClientSetup);

        MovementPlus.init();
    }

    private void onConfigLoading(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == ServerConfig.SERVER_SPEC) {
            ServerConfig.cache();
        }
    }

    private void onConfigReloading(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == ServerConfig.SERVER_SPEC) {
            ServerConfig.cache();
            ConfigSyncPayload.syncToAll();
        }
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        MovementPlusClient.init();
    }
}
