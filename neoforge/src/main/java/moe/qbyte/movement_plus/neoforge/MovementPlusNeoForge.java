package moe.qbyte.movement_plus.neoforge;

import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.MovementPlusClient;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.config.ServerConfig;
import moe.qbyte.movement_plus.registry.ModAttributes;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import java.util.Arrays;

@Mod(MovementPlus.MOD_ID)
public final class MovementPlusNeoForge {
    private final ModContainer container;

    public MovementPlusNeoForge(IEventBus modBus, ModContainer container) {
        this.container = container;
        container.registerConfig(syncedConfigType(), ServerConfig.SERVER_SPEC);
        modBus.addListener(this::onConfigLoading);
        modBus.addListener(this::onConfigReloading);
        modBus.addListener(this::onEntityAttributeModification);
        modBus.addListener(this::onClientSetup);

        MovementPlus.init();
    }

    /**
     * NeoForge 26.3 renamed {@code ModConfig.Type.SERVER} to {@code SYNCED};
     * looking the constant up by name keeps one jar loadable on both 26.2 and 26.3.
     * 
     * As soon as 26.2 support is dropped this can be savely removed and just be
     * replaced with ModConfig.Type.SYNCED
     */
    private static ModConfig.Type syncedConfigType() {
        for (ModConfig.Type type : ModConfig.Type.values()) {
            if (type.name().equals("SYNCED") || type.name().equals("SERVER")) {
                return type;
            }
        }
        throw new IllegalStateException("No synced config type in " + Arrays.toString(ModConfig.Type.values()));
    }

    private void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityTypes.PLAYER, ModAttributes.MULTI_JUMPS.holder());
        event.add(EntityTypes.PLAYER, ModAttributes.COYOTE_TIME.holder());
        event.add(EntityTypes.PLAYER, ModAttributes.SWIM_SPEED.holder());
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

        // NeoForge's built-in config screen, reachable from the mod list
        this.container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
