package Booster;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

/**
 * Created by A.K. on 14/05/31.
 */
public class MessageSpawnCloud implements IMessage, IMessageHandler<MessageSpawnCloud, IMessage> {

    boolean spawnCloud;
    double posX;
    double posY;
    double posZ;

    public MessageSpawnCloud(){}

    public MessageSpawnCloud(boolean par1, double par2, double par3, double par4) {
        spawnCloud = par1;
        posX = par2;
        posY = par3;
        posZ = par4;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        spawnCloud = buf.readBoolean();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(spawnCloud);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    @Override
    public IMessage onMessage(MessageSpawnCloud message, MessageContext ctx) {
        World world = ctx.getServerHandler().playerEntity.worldObj;
        world.spawnParticle("cloud", message.posX, message.posY, message.posZ, 0.0D, 0.0D, 0.0D);
        return null;
    }
}
