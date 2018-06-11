package Booster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import static Booster.Booster.MOD_ID;

public class ClientProxy extends CommonProxy {
    public static KeyBinding boostKey = new KeyBinding("Key.BoosterSwitch", Keyboard.KEY_B, "Booster");

    @Override
    public void registerClientInformation() {
        ClientRegistry.registerKeyBinding(boostKey);
        MinecraftForge.EVENT_BUS.register(new ClientEventHooks());
        registerItemModel(Booster.Booster08, "booster08");
        registerItemModel(Booster.Booster20, "booster20");
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().world;
    }

    private void registerItemModel(Item item, String registeredName) {
        ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        itemModelMesher.register(item, 0, new ModelResourceLocation(MOD_ID + ":" + registeredName, "inventory"));
    }
}