package Booster;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.logging.Logger;

@Mod(modid = Booster.MOD_ID,
        name = Booster.MOD_NAME,
        version = Booster.MOD_VERSION,
        dependencies = Booster.MOD_DEPENDENCIES,
        useMetadata = true, acceptedMinecraftVersions = Booster.MOD_MC_VERSION)
public class Booster {
    public static final String MOD_ID = "booster";
    public static final String MOD_NAME = "Booster";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:forge";
    public static final String MOD_MC_VERSION = "[1.11,1.99.99]";
    public static final String TextureDomain = "booster:";
    public static final String Armor08_1 = "textures/armor/armor_08_1.png";
    public static final String Armor20_1 = "textures/armor/armor_20_1.png";
    public static final Logger LOGGER = Logger.getLogger("Booster");
    public static Item Booster08;
    public static Item Booster20;
    public static int boostPower;
    public static boolean boosterDefaultSwitch;
    public static double movement;
    public static LivingEventHooks livingeventhooks;
    @SidedProxy(clientSide = "Booster.ClientProxy", serverSide = "Booster.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        boostPower = config.get(Configuration.CATEGORY_GENERAL, "boostPower", 25).getInt();
        boosterDefaultSwitch = config.get(Configuration.CATEGORY_GENERAL, "boosterDefaultSwitch", true).getBoolean(true);
        movement = config.get(Configuration.CATEGORY_GENERAL, "movement", 1d).getDouble(1);
        config.save();
        Booster08 = new ItemBooster(ItemArmor.ArmorMaterial.IRON, 2, EntityEquipmentSlot.CHEST, BoosterType.BOOSTER_08)
                .setRegistryName("booster08").setUnlocalizedName(TextureDomain + "Booster08").setCreativeTab(CreativeTabs.COMBAT);
        GameRegistry.register(Booster08);
        Booster20 = new ItemBooster(ItemArmor.ArmorMaterial.DIAMOND, 3, EntityEquipmentSlot.CHEST, BoosterType.BOOSTER_20)
                .setRegistryName("booster20").setUnlocalizedName(TextureDomain + "Booster20").setCreativeTab(CreativeTabs.COMBAT);
        GameRegistry.register(Booster20);
        PacketHandler.init();
        CapabilityPlayerBoosterStatusHandler.register();
        livingeventhooks = new LivingEventHooks();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerClientInformation();

        MinecraftForge.EVENT_BUS.register(livingeventhooks);
        GameRegistry.addRecipe(new ItemStack(Booster08),
                "XRX", "XPX", "X X",
                'X', Items.IRON_INGOT,
                'R', Items.REPEATER,
                'P', Blocks.PISTON);

        GameRegistry.addRecipe(new ItemStack(Booster20),
                "I I", " B ", "IDI",
                'B', Booster08,
                'I', Items.IRON_INGOT,
                'D', Items.DIAMOND);
    }
}