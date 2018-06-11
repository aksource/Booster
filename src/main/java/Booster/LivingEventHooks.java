package Booster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class LivingEventHooks {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onPlayerFall(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            if (checkBoosterWearing(player) && player.isSneaking()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(CapabilityPlayerBoosterStatusHandler.BOOSTER_STATUS, new PlayerBoosterStatusHandler.PlayerBoosterStatusImpl());
        }
    }

    public static boolean checkBoosterWearing (EntityPlayer player) {
        ItemStack chestArmor = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        return !player.capabilities.isCreativeMode && ((!chestArmor.isEmpty() && (chestArmor.getItem() instanceof ItemBooster)));
    }
}