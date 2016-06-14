package Booster;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

/**
 * Created by A.K. on 14/11/25.
 */
@SideOnly(Side.CLIENT)
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
            boolean boosterSwitch = !PlayerBoosterProperties.get(player).isBoosterSwitch();
            String switchData;
            if(boosterSwitch) {
                switchData = "ON";
            } else {
                switchData = "OFF";
            }
            player.addChatMessage(new ChatComponentText(String.format("BoosterSwitch - %s", switchData)));
            PlayerBoosterProperties.get(player).setBoosterSwitch(boosterSwitch);
        }
    }

    @SubscribeEvent
    public void doPlayerClientTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient()
                && event.phase == TickEvent.Phase.END
                && event.player instanceof EntityPlayerSP
                && LivingEventHooks.checkBoosterWearing(event.player)) {
            EntityPlayerSP playerSP = (EntityPlayerSP)event.player;
            boolean boosterSwitch = PlayerBoosterProperties.get(playerSP).isBoosterSwitch();
            if (boosterSwitch) {
                int boostPower = PlayerBoosterProperties.get(playerSP).getBoosterPower();
                if (playerSP.onGround && boostPower <= 0) {
                    int durability = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, playerSP.getCurrentArmor(2));
                    PlayerBoosterProperties.get(playerSP).setBoosterPower(Booster.BoostPower * (durability + 1));
                }

                if (!playerSP.onGround && boostPower > 0) {
                    playerSP.movementInput.updatePlayerMoveState();
                    boostPower--;
                    PlayerBoosterProperties.get(playerSP).setBoosterPower(boostPower);
                    boolean moveCheck;
                    float f1 = playerSP.rotationYaw * (2 * (float)Math.PI / 360);
                    double dx = MathHelper.sin(f1);
                    double dz = MathHelper.cos(f1);
                    if (isBooster08(playerSP)) {
                        moveCheck = moveBooster08(playerSP);
                        playerSP.worldObj.spawnParticle("cloud", playerSP.posX + dx, playerSP.posY + 0.1D, playerSP.posZ - dz, 0.0D, 0.0D, 0.0D);
                        PacketHandler.INSTANCE.sendToServer(new MessageSpawnCloud(moveCheck, playerSP.posX + dx, playerSP.posY + 0.1D, playerSP.posZ - dz));
                    }

                    if (isBooster20(playerSP)) {
                        moveCheck = moveXZBooster20(playerSP) || moveYBooster20(playerSP);
                        playerSP.worldObj.spawnParticle("cloud", playerSP.posX + dx, playerSP.posY + 0.1D, playerSP.posZ - dz, 0.0D, 0.0D, 0.0D);
                        PacketHandler.INSTANCE.sendToServer(new MessageSpawnCloud(moveCheck, playerSP.posX + dx, playerSP.posY + 0.1D, playerSP.posZ - dz));
                    }
                    playerSP.movementInput.updatePlayerMoveState();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static boolean moveXZBooster20(EntityPlayerSP player) {
        float moveForward = player.movementInput.moveForward;
        float moveStrafe = player.movementInput.moveStrafe;
//        float f1 = player.rotationYaw * (2 * (float)Math.PI / 360);
        if (moveForward != 0 || moveStrafe != 0) {
            player.motionY = player.motionX = player.motionZ = 0;
        }

//        player.motionX -= MathHelper.sin(f1 - (float) (Math.PI / 2) * Math.signum(moveStrafe)) * getMovementFactor() * signumZeroException(moveForward, moveStrafe);
//        player.motionZ += MathHelper.cos(f1 - (float)(Math.PI / 2) * Math.signum(moveStrafe)) * getMovementFactor() * signumZeroException(moveForward, moveStrafe);
        player.moveFlying(moveStrafe, moveForward, (float)getMovementFactor());
        return player.motionX != 0 || player.motionZ != 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean moveYBooster20(EntityPlayerSP player) {
        boolean isJumping = player.movementInput.jump/*Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed()*/;
        boolean isSneaking = player.movementInput.sneak/*Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed()*/;
        if (isJumping || isSneaking) {
            player.motionY =/* player.motionX = player.motionZ =*/ 0;
        }
        if (isJumping) {
            player.motionY += getMovementFactor();
            return true;
        } else if (isSneaking) {
            player.motionY -= getMovementFactor();
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public static boolean moveBooster08(EntityPlayerSP player) {
        boolean enableToJump = player.movementInput.jump;
        if(!player.onGround && enableToJump) {
            player.motionY = /*player.motionX = player.motionZ = */0;
            player.motionY += getMovementFactor();
            return true;
        }
        return false;
    }

    private static float signumZeroException(float f, float check) {
        return (check != 0.0f && f == 0.0f) ? 1.0f : Math.signum(f);
    }

    public static boolean isBooster08(EntityPlayer player) {
        ItemStack chestArmor = player.getCurrentArmor(2);
        return chestArmor != null && chestArmor.getItem() == Booster.Booster08;
    }

    public static boolean isBooster20(EntityPlayer player) {
        ItemStack chestArmor = player.getCurrentArmor(2);
        return chestArmor != null && chestArmor.getItem() == Booster.Booster20;
    }

    public static double getMovementFactor()
    {
        return Booster.movement * 0.5d;
    }
}
