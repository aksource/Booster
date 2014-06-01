package Booster;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by A.K. on 14/05/31.
 */
public class MessageKeyPressed implements IMessage, IMessageHandler<MessageKeyPressed, IMessage>{

    public boolean switchBool;

    public MessageKeyPressed(){}

    public MessageKeyPressed(boolean par1) {
        this.switchBool = par1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.switchBool = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.switchBool);
    }

    @Override
    public IMessage onMessage(MessageKeyPressed message, MessageContext ctx) {
        Booster.livingeventhooks.boosterSwitch = message.switchBool;
        return null;
    }
}
