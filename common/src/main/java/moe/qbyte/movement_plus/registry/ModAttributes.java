package moe.qbyte.movement_plus.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import moe.qbyte.movement_plus.MovementPlus;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.function.Supplier;

/**
 * Custom player attributes; attached to players in {@code PlayerMixin#createAttributes}.
 *
 * <p>The old {@code movement_plus:jump_height} attribute is gone: since 1.20.5 vanilla ships
 * {@code minecraft:generic.jump_strength} on all living entities, which fills the same role.
 */
public final class ModAttributes {
    private static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(MovementPlus.MOD_ID, Registries.ATTRIBUTE);

    public static final Entry MULTI_JUMPS = register("multi_jumps",
            () -> new RangedAttribute("attribute.name.player.movement_plus.multi_jumps", 0d, 0d, 1024d).setSyncable(true));
    public static final Entry COYOTE_TIME = register("coyote_time",
            () -> new RangedAttribute("attribute.name.player.movement_plus.coyote_time", 0d, 0d, 4096d).setSyncable(true));
    public static final Entry SWIM_SPEED = register("swim_speed",
            () -> new RangedAttribute("attribute.name.player.movement_plus.swim_speed", 1d, 0d, 1024d).setSyncable(true));

    private ModAttributes() {}

    private static Entry register(String name, Supplier<Attribute> supplier) {
        return new Entry(ATTRIBUTES.register(name, supplier));
    }

    public static void register() {
        ATTRIBUTES.register();
    }

    public record Entry(RegistrySupplier<Attribute> supplier) {
        public Attribute get() {
            return this.supplier.get();
        }

        public Holder<Attribute> holder() {
            return BuiltInRegistries.ATTRIBUTE.wrapAsHolder(this.supplier.get());
        }
    }
}
