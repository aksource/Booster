package Booster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding boostKey = new KeyBinding("Key.BoosterSwitch",Keyboard.KEY_B, "Booster");
	@Override
	public void registerClientInformation()
	{
		ClientRegistry.registerKeyBinding(boostKey);

        FMLCommonHandler.instance().bus().register(new ClientEventHooks());
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Booster.Booster08, 0, new ModelResourceLocation(Booster.MOD_ID + ":" + "booster08", "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Booster.Booster20, 0, new ModelResourceLocation(Booster.MOD_ID + ":" + "booster20", "inventory"));
    }

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}