package dev.necro.coyotelib;

import dev.necro.coyotelib.client.ClientProxy;
import dev.necro.coyotelib.client.config.ClientConfiguration;
import dev.necro.coyotelib.common.CommonProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(CoyoteLib.MODID)
public class CoyoteLib
{
    public static final String MODID = "coyotelib";

    public static CommonProxy proxy;

    public CoyoteLib() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfiguration.CONFIGURATION_SPEC);
        CoyoteLib.proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }
}
