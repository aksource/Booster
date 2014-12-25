package Booster;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by A.K. on 14/05/31.
 */
public class MessageSpawnCloud implements IMessage, IMessageHandler<MessageSpawnCloud, IMessage> {

    boolean spawnCloud;

    public MessageSpawnCloud(){}

    public MessageSpawnCloud(boolean par1) {
        spawnCloud = par1;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        spawnCloud = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(spawnCloud);
    }

    @Override
    public IMessage onMessage(MessageSpawnCloud message, MessageContext ctx) {
        Booster.livingeventhooks.spawnCloud = message.spawnCloud;
        return null;
    }
}
