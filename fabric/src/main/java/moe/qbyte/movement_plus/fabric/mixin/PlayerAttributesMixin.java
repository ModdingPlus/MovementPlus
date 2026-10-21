package moe.qbyte.movement_plus.fabric.mixin;

import moe.qbyte.movement_plus.registry.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Adds our attributes to every player attribute builder instead of replacing the
 * player's default supplier, which would wipe attributes other mods registered through
 * {@code FabricDefaultAttributeRegistry} (and vice versa), depending on init order.
 */
@Mixin(Player.class)
public abstract class PlayerAttributesMixin {

    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void movement_plus$addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        // Vanilla builds the default suppliers before mod init in dev (Bootstrap.validate()),
        // and other mods may call this before our attributes are registered; skip those calls,
        // MovementPlusFabric adds them to the registered supplier afterwards.
        for (ModAttributes.Entry entry : ModAttributes.all()) {
            if (entry.supplier().isPresent()) {
                cir.getReturnValue().add(entry.holder());
            }
        }
    }
}
