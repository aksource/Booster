package Booster;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * Created by A.K. on 14/11/25.
 */
public class ClientEventHooks {


    @SubscribeEvent
    public void KeyPressEvent(InputEvent.KeyInputEvent event)
    {
        if (ClientProxy.boostKey.isPressed()) {
            boostKeyCheck(FMLClientHandler.instance().getClientPlayerEntity());
        }
    }

    @SideOnly(Side.CLIENT)
    public void boostKeyCheck(EntityPlayer player)
    {
        if (player != null) {
            boolean boosterSwitch = !Booster.livingeventhooks.boosterSwitch;
            String switchData;
            if(boosterSwitch) {
                switchData = "ON";
            } else {
                switchData = "OFF";
            }
            player.addChatMessage(new ChatComponentText(String.format("BoosterSwitch - %s", switchData)));
            PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(boosterSwitch));
        }
    }
}
