package moe.qbit.movement_plus;

import moe.qbit.movement_plus.client.ClientProxy;
import moe.qbit.movement_plus.common.CommonProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(MovementPlus.MODID)
public class MovementPlus
{
    public static final String MODID = "movement_plus";

    public static CommonProxy proxy;

    public MovementPlus() {
        MovementPlus.proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }
}
