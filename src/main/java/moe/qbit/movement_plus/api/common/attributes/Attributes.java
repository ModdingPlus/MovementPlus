package moe.qbit.movement_plus.api.common.attributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.RegistryObject;

public class Attributes {
    public static void fill(IAttributeProvider provider){
        JUMP_HEIGHT=provider.getJumpHeightAttribute();
        MULTI_JUMPS=provider.getMultiJumpsAttribute();
        COYOTE_TIME=provider.getCoyoteTimeAttribute();
    }

    public static RegistryObject<Attribute> JUMP_HEIGHT;
    public static RegistryObject<Attribute> MULTI_JUMPS;
    public static RegistryObject<Attribute> COYOTE_TIME;
}
