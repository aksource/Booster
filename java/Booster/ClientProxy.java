package Booster;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
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
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}