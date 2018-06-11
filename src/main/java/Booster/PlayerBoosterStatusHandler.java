package Booster;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

/**
 * ブースターの設定保持Capabilityクラス
 * Created by A.K. on 2017/04/08.
 */
public interface PlayerBoosterStatusHandler {
    boolean isBoosterSwitch();

    void setBoosterSwitch(boolean boosterSwitch);

    int getBoosterPower();

    void setBoosterPower(int boosterPower);

    class PlayerBoosterStatusImpl implements PlayerBoosterStatusHandler, ICapabilitySerializable<NBTTagCompound> {

        private boolean boosterSwitch = Booster.boosterDefaultSwitch;
        private int boosterPower = Booster.boostPower;
        @Override
        public boolean isBoosterSwitch() {
            return this.boosterSwitch;
        }

        @Override
        public void setBoosterSwitch(boolean boosterSwitch) {
            this.boosterSwitch = boosterSwitch;
        }

        @Override
        public int getBoosterPower() {
            return this.boosterPower;
        }

        @Override
        public void setBoosterPower(int boosterPower) {
            this.boosterPower = boosterPower;
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityPlayerBoosterStatusHandler.CAPABILITY_BOOSTER_STATUS;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            return hasCapability(capability, facing) ? CapabilityPlayerBoosterStatusHandler.CAPABILITY_BOOSTER_STATUS.cast(this) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean("boosterSwitch", this.isBoosterSwitch());
            nbt.setInteger("boosterPower", this.getBoosterPower());
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            this.setBoosterSwitch(nbt.getBoolean("boosterSwitch"));
            this.setBoosterPower(nbt.getInteger("boosterPower"));
        }

        public static PlayerBoosterStatusHandler get(EntityPlayer player) {
            return player.getCapability(CapabilityPlayerBoosterStatusHandler.CAPABILITY_BOOSTER_STATUS, null);
        }
    }
}
