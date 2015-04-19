package Booster;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;


public class LivingEventHooks {

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.entityLiving;
            if (checkBoosterWearing(player) && player.isSneaking()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            PlayerBoosterProperties.register((EntityPlayer) event.entity);
        }
    }

    public static boolean checkBoosterWearing(EntityPlayer player) {
        ItemStack chestArmor = player.getCurrentArmor(2);
        return !player.capabilities.isCreativeMode && (/*Booster.Alwaysflying || */(chestArmor != null && (chestArmor.getItem() instanceof ItemBooster)));
    }
}