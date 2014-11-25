package Booster;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
