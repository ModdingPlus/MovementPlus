package moe.qbit.movement_plus.common.attributes;

import moe.qbit.movement_plus.MovementPlus;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MovementPlus.MODID)
public class ModAttributes {
    protected static DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MovementPlus.MODID);
    public static RegistryObject<Attribute> JUMP_HEIGHT = ATTRIBUTES.register("jump_height", () -> new RangedAttribute("jump_height", 0.42d, 0d, 1024d).setSyncable(true) );
    public static RegistryObject<Attribute> MULTI_JUMPS = ATTRIBUTES.register("multi_jumps", () -> new RangedAttribute("multi_jumps", 0d, 0d, 1024d).setSyncable(true) );
    public static RegistryObject<Attribute> COYOTE_TIME = ATTRIBUTES.register("coyote_time", () -> new RangedAttribute("coyote_time", 0d, 0d, 4096d).setSyncable(true) );
    
    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event){
        event.getTypes().forEach(type -> {
            event.add(type, JUMP_HEIGHT.get());
        });
        event.add(EntityType.PLAYER, MULTI_JUMPS.get());
        event.add(EntityType.PLAYER, COYOTE_TIME.get());
    }
}
