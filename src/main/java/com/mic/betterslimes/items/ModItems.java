package com.mic.betterslimes.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {

    public static final List<Item> MOD_ITEMS = new ArrayList<>();

	public static final Item BLUE_SLIME = registerItem("blue_slime");
	public static final Item RED_SLIME = registerItem("red_slime");
	public static final Item YELLOW_SLIME = registerItem("yellow_slime");
	public static final Item PURPLE_SLIME = registerItem("purple_slime");
	public static final Item BLACK_SLIME = registerItem("black_slime");

    private static Item registerItem(String name) {
        return registerItem(name, CreativeTabs.MISC);
    }

    private static Item registerItem(String name, CreativeTabs tab) {
        Item item = new Item();
        item.setUnlocalizedName(name);
		item.setRegistryName(name);
		
		item.setCreativeTab(tab);
        MOD_ITEMS.add(item);

        return item;
    }

	public static void registerOreDict() {
		OreDictionary.registerOre("slimeball", BLUE_SLIME);
		OreDictionary.registerOre("slimeball", RED_SLIME);
		OreDictionary.registerOre("slimeball", YELLOW_SLIME);
		OreDictionary.registerOre("slimeball", PURPLE_SLIME);
		OreDictionary.registerOre("slimeball", BLACK_SLIME);
    }
}
