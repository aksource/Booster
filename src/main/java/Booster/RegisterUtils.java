package Booster;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static Booster.Booster.Booster08;
import static Booster.Booster.Booster20;

/**
 * Created by A.K. on 2018/06/08.
 */
public class RegisterUtils {

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(Booster08);
        registry.register(Booster20);
    }
}
