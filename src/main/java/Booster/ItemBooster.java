package Booster;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

enum BoosterType {
	BOOSTER_08,
	BOOSTER_20
}

public class ItemBooster extends ItemArmor {
	private final BoosterType type;
	public ItemBooster(ItemArmor.ArmorMaterial armorMaterial, int renderIndexIn, EntityEquipmentSlot equipmentSlot, BoosterType type) {
		super(armorMaterial, renderIndexIn, equipmentSlot);
		this.type = type;
	}

	@Override
	@Nonnull
	public String getArmorTexture(@Nonnull ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		String st = "";
		if (!stack.isEmpty() && stack.getItem() instanceof ItemBooster) {
			ItemBooster booster = (ItemBooster)stack.getItem();
			if(booster.type == BoosterType.BOOSTER_08) {
				st = Booster.TextureDomain + Booster.Armor08_1;
			} else if(booster.type == BoosterType.BOOSTER_20){
				st = Booster.TextureDomain + Booster.Armor20_1;
			}
		}
		return st;
	}
}
