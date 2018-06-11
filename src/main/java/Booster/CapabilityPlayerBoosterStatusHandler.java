package Booster;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * ブースターのステータスハンドリングクラス
 * Created by A.K. on 2017/04/08.
 */
public class CapabilityPlayerBoosterStatusHandler {
    public final static ResourceLocation BOOSTER_STATUS = new ResourceLocation(Booster.MOD_ID, "status");
    @CapabilityInject(PlayerBoosterStatusHandler.class)
    public static Capability<PlayerBoosterStatusHandler> CAPABILITY_BOOSTER_STATUS = null;
    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerBoosterStatusHandler.class, new Capability.IStorage<PlayerBoosterStatusHandler>() {

            @Override
            public void readNBT(Capability<PlayerBoosterStatusHandler> capability, PlayerBoosterStatusHandler instance, EnumFacing side, NBTBase nbt) {
                NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
                instance.setBoosterSwitch(nbtTagCompound.getBoolean("boosterSwitch"));
                instance.setBoosterPower(nbtTagCompound.getInteger("boosterPower"));
            }

            @Override
            public NBTBase writeNBT(Capability<PlayerBoosterStatusHandler> capability, PlayerBoosterStatusHandler instance, EnumFacing side) {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean("boosterSwitch", instance.isBoosterSwitch());
                nbt.setInteger("boosterPower", instance.getBoosterPower());
                return nbt;
            }
        }, PlayerBoosterStatusHandler.PlayerBoosterStatusImpl::new);
    }
}
