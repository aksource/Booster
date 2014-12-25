package Booster;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class LivingEventHooks
{
	private int boostPower;
    public boolean boosterSwitch = Booster.BoosterDefaultSwitch;
	public boolean spawnCloud;

	@SubscribeEvent
	public void LivingUpdate(LivingUpdateEvent event)
	{
		if(event.entityLiving != null && event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boost(player, player.worldObj);
		}
	}


	private void boost(EntityPlayer ep, World world)
	{
		if(checkBoosterWearing(ep)) {
            int durability = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, ep.getCurrentArmor(2));
            if (boostPower <= 0) {
                boostPower = Booster.BoostPower * (durability + 1);
            }
			if(!ep.onGround && boosterSwitch) {

				if(boostPower > 0 || Booster.Alwaysflying) {
					if(world.isRemote) {
                        this.spawnCloud = false;
						if(Booster.Alwaysflying || isBooster20(ep)) {
                            moveXZBooster20(ep);
                            moveYBooster20(ep);
//                            moveBooster20old(ep, enableToJump);
						} else if(isBooster08(ep)) {
                            moveBooster08(ep);
						}
                        PacketHandler.INSTANCE.sendToServer(new MessageSpawnCloud(spawnCloud));
					}
                    if(this.spawnCloud)
						commonProcess(ep, world);
//				} else {
//					world.spawnParticle("smoke", ep.posX, ep.posY + 0.1D, ep.posZ, 0.0D, 0.0D, 0.0D);
				} else {
                    this.spawnCloud = false;
                }
			} else if(boosterSwitch) {
                boostPower = Booster.BoostPower * (durability + 1);
			}
            if(checkBoosterWearing(ep) && ep.isSneaking()) {
                ep.fallDistance = 0.0F;
            }
		}
	}

    @SideOnly(Side.CLIENT)
    private void moveXZBooster20(EntityPlayer player) {
        EntityPlayerSP playerSP = (EntityPlayerSP)player;
        float moveForward = playerSP.movementInput.moveForward;
        float moveStrafe = playerSP.movementInput.moveStrafe;
        float f1 = player.rotationYaw * (2 * (float)Math.PI / 360);
        if (moveForward != 0 || moveStrafe != 0) {
            player.motionY = player.motionX = player.motionZ = 0;
            this.spawnCloud = true;
        }

        player.motionX -= MathHelper.sin(f1 - (float)(Math.PI / 2) * Math.signum(moveStrafe)) * getMovementFactor() * signumZeroException(moveForward, moveStrafe);
        player.motionZ += MathHelper.cos(f1 - (float)(Math.PI / 2) * Math.signum(moveStrafe)) * getMovementFactor() * signumZeroException(moveForward, moveStrafe);
    }

    @SideOnly(Side.CLIENT)
    private void moveYBooster20(EntityPlayer player) {
        EntityPlayerSP playerSP = (EntityPlayerSP)player;
        boolean enableToJump = playerSP.movementInput.jump;
        if (playerSP.movementInput.jump || playerSP.movementInput.sneak) {
            player.motionY = player.motionX = player.motionZ = 0;
            this.spawnCloud = true;
        }
        if (enableToJump) {
            player.motionY += getMovementFactor();
        } else if (playerSP.movementInput.sneak) {
            player.motionY -= getMovementFactor();
        }
    }

    @SideOnly(Side.CLIENT)
    private void moveBooster08(EntityPlayer player) {
        EntityPlayerSP playerSP = (EntityPlayerSP) player;
        boolean enableToJump = playerSP.movementInput.jump;
        if(!player.onGround && enableToJump) {
            player.motionY = player.motionX = player.motionZ = 0;
            player.motionY += getMovementFactor();
            this.spawnCloud = true;
        }
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    private void moveBooster20old(EntityPlayer ep, boolean enableToJump) {
        EntityPlayerSP epsp = (EntityPlayerSP) ep;
        if(epsp.movementInput.moveForward > 0) {
            float f1 = ep.rotationYaw * (2 * (float)Math.PI / 360);
            ep.motionY=ep.motionX=ep.motionZ =0;
            ep.motionX -= MathHelper.sin(f1) * getMovementFactor();
            ep.motionZ += MathHelper.cos(f1) * getMovementFactor();
            this.spawnCloud = true;
        } else if(epsp.movementInput.moveForward < 0) {
            float f1 = ep.rotationYaw * (2 * (float)Math.PI / 360);
            ep.motionY=ep.motionX=ep.motionZ =0;
            ep.motionX -= MathHelper.sin(f1) * -getMovementFactor();
            ep.motionZ += MathHelper.cos(f1) * -getMovementFactor();
            this.spawnCloud = true;
        } else if(epsp.movementInput.moveStrafe < 0) {
            float f1 = ep.rotationYaw * (2 * (float)Math.PI / 360);
            ep.motionY=ep.motionX=ep.motionZ =0;
            ep.motionX -= MathHelper.sin(f1 + (float)(Math.PI / 2)) * getMovementFactor();
            ep.motionZ += MathHelper.cos(f1 + (float)(Math.PI / 2)) * getMovementFactor();
            this.spawnCloud = true;
        } else if(epsp.movementInput.moveStrafe > 0) {
            float f1 = ep.rotationYaw * (2 * (float)Math.PI / 360);
            ep.motionY=ep.motionX=ep.motionZ =0;
            ep.motionX -= MathHelper.sin(f1 - (float)(Math.PI / 2)) * getMovementFactor();
            ep.motionZ += MathHelper.cos(f1 - (float)(Math.PI / 2)) * getMovementFactor();
            this.spawnCloud = true;
        } else if(epsp.movementInput.jump && enableToJump) {
            ep.motionY=ep.motionX=ep.motionZ =0;
            ep.motionY += getMovementFactor();
            this.spawnCloud = true;
        } else if(epsp.movementInput.sneak) {
            ep.motionY=ep.motionX=ep.motionZ =0;
            ep.motionY -= getMovementFactor();
            this.spawnCloud = true;
        }
    }

    private float signumZeroException(float f, float check) {
        return (check != 0.0f && f == 0.0f) ? 1.0f : Math.signum(f);
    }

    private boolean isBooster08(EntityPlayer player) {
        ItemStack chestArmor = player.getCurrentArmor(2);
        return chestArmor != null && chestArmor.getItem() == Booster.Booster08;
    }

    private boolean isBooster20(EntityPlayer player) {
        ItemStack chestArmor = player.getCurrentArmor(2);
        return chestArmor != null && chestArmor.getItem() == Booster.Booster20;
    }

    private boolean checkBoosterWearing (EntityPlayer player) {
        ItemStack chestArmor = player.getCurrentArmor(2);
        return !player.capabilities.isCreativeMode && (Booster.Alwaysflying || (chestArmor != null && (chestArmor.getItem() instanceof ItemBooster)));
    }

	private void commonProcess(EntityPlayer ep, World world)
	{
		world.spawnParticle(EnumParticleTypes.CLOUD, ep.posX, ep.posY + 0.1D, ep.posZ, 0.0D, 0.0D, 0.0D);
		boostPower--;
		ep.fallDistance = 0F;
	}

	private double getMovementFactor()
	{
		return Booster.movement * 0.5d;
	}
}