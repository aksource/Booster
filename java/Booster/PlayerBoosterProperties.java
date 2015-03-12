package Booster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by A.K. on 15/02/15.
 */
public class PlayerBoosterProperties implements IExtendedEntityProperties {
    public final static String EXT_PROP_NAME = "Booster:BoosterData";
    private boolean boosterSwitch = Booster.BoosterDefaultSwitch;
    private int boosterPower = Booster.BoostPower;

    private static String getSaveKey(EntityPlayer player) {
        return player.getGameProfile().getId().toString() + ":" + EXT_PROP_NAME;
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(EXT_PROP_NAME, new PlayerBoosterProperties());
    }

    public static PlayerBoosterProperties get(EntityPlayer player) {
        return (PlayerBoosterProperties)player.getExtendedProperties(EXT_PROP_NAME);
    }
    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("boosterSwitch", this.boosterSwitch);
        nbt.setInteger("boosterPower", this.boosterPower);
        compound.setTag(EXT_PROP_NAME, nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
        this.boosterSwitch = nbt.getBoolean("boosterSwitch");
        this.boosterPower = nbt.getInteger("bosterPower");
    }

    @Override
    public void init(Entity entity, World world) {
        //NO-OP
    }

    public boolean isBoosterSwitch() {
        return boosterSwitch;
    }

    public void setBoosterSwitch(boolean boosterSwitch) {
        this.boosterSwitch = boosterSwitch;
    }

    public int getBoosterPower() {
        return boosterPower;
    }

    public void setBoosterPower(int boosterPower) {
        this.boosterPower = boosterPower;
    }
}
