package Booster;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by A.K. on 14/05/31.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("booster");

    public static void init() {
        INSTANCE.registerMessage(MessageSpawnCloud.class, MessageSpawnCloud.class, 0, Side.SERVER);
    }
}
