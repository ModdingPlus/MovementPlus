package moe.qbyte.movement_plus.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.core.Holder;

/**
 * Maintains config-driven multipliers as transient attribute modifiers instead of
 * writing the attribute base, so other mods keep full control of the base value.
 * Transient modifiers are not saved to NBT, so nothing lingers after the mod or
 * feature is removed. A multiplier of 1 means no modifier at all.
 */
public final class AttributeMultipliers {
    private AttributeMultipliers() {}

    public static void apply(LivingEntity entity, Holder<Attribute> attribute,
                             ResourceLocation modifierId, double multiplier) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null) return;

        if (multiplier == 1.0d) {
            instance.removeModifier(modifierId);
            return;
        }

        AttributeModifier existing = instance.getModifier(modifierId);
        double amount = multiplier - 1.0d;
        if (existing == null || existing.amount() != amount) {
            instance.addOrUpdateTransientModifier(new AttributeModifier(
                    modifierId, amount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }
}
