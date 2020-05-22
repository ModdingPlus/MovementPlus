package dev.necro.coyotelib;

import dev.necro.coyotelib.client.ClientProxy;
import dev.necro.coyotelib.common.CommonProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CoyoteLib.MODID)
public class CoyoteLib
{
    public static final String MODID = "coyotelib";

    private static final Logger LOGGER = LogManager.getLogger();

    public static CommonProxy proxy;

    public CoyoteLib() {
        CoyoteLib.proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }
}
