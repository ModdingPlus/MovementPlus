package moe.qbyte.movement_plus.fabric.client;

import fuzs.forgeconfigapiport.fabric.api.v5.client.ConfigScreenFactoryRegistry;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.MovementPlusClient;
import net.fabricmc.api.ClientModInitializer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public final class MovementPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MovementPlusClient.init();

        // FCAP's port of NeoForge's config screen, exposed through Mod Menu
        ConfigScreenFactoryRegistry.INSTANCE.register(MovementPlus.MOD_ID, ConfigurationScreen::new);
    }
}
