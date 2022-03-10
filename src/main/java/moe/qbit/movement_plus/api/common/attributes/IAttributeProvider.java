package moe.qbit.movement_plus.api.common.attributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.RegistryObject;

public interface IAttributeProvider {
    RegistryObject<Attribute> getJumpHeightAttribute();
    RegistryObject<Attribute> getMultiJumpsAttribute();
    RegistryObject<Attribute> getCoyoteTimeAttribute();
}
