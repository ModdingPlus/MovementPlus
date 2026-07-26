package moe.qbyte.movement_plus.common;

import net.minecraft.resources.Identifier;
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
                             Identifier modifierId, double multiplier) {
        set(entity, attribute, modifierId, multiplier - 1.0d, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    /** Flat bonus variant with the same lifecycle: an amount of 0 removes the modifier. */
    public static void applyFlat(LivingEntity entity, Holder<Attribute> attribute,
                                 Identifier modifierId, double amount) {
        set(entity, attribute, modifierId, amount, AttributeModifier.Operation.ADD_VALUE);
    }

    private static void set(LivingEntity entity, Holder<Attribute> attribute,
                            Identifier modifierId, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null) return;

        if (amount == 0.0d) {
            instance.removeModifier(modifierId);
            return;
        }

        AttributeModifier existing = instance.getModifier(modifierId);
        if (existing == null || existing.amount() != amount) {
            instance.addOrUpdateTransientModifier(new AttributeModifier(modifierId, amount, operation));
        }
    }
}
