package moe.qbyte.movement_plus.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import moe.qbyte.movement_plus.MovementPlus;
import moe.qbyte.movement_plus.config.ConfigSyncPayload;
import moe.qbyte.movement_plus.config.ServerConfig;
import moe.qbyte.movement_plus.fabric.mixin.AttributeSupplierAccessor;
import moe.qbyte.movement_plus.registry.ModAttributes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
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

        // Extends the player's current default supplier rather than replacing it, keeping
        // attributes mods initialized before us registered. Mods initialized after us that
        // build from Player.createAttributes() get ours through PlayerAttributesMixin.
        AttributeSupplier.Builder builder = AttributeSupplier.builder();
        ((AttributeSupplierAccessor) DefaultAttributes.getSupplier(EntityType.PLAYER)).movement_plus$getInstances()
                .forEach((attribute, instance) -> builder.add(attribute, instance.getBaseValue()));
        for (ModAttributes.Entry entry : ModAttributes.all()) {
            builder.add(entry.holder());
        }
        FabricDefaultAttributeRegistry.register(EntityType.PLAYER, builder);
    }
}
