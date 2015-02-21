package Booster;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by A.K. on 14/05/31.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("booster");

    public static void init() {
        INSTANCE.registerMessage(MessageSpawnCloud.class, MessageSpawnCloud.class, 0, Side.SERVER);
    }
}
