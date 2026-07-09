package moe.qbyte.movement_plus.fabric.client;

import moe.qbyte.movement_plus.MovementPlusClient;
import net.fabricmc.api.ClientModInitializer;

public final class MovementPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MovementPlusClient.init();
    }
}
