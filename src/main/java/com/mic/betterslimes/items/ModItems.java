package com.mic.betterslimes.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {

    public static final List<Item> MOD_ITEMS = new ArrayList<>();

	public static final Item blue_slime = new ItemBase("blue_slime");
	public static final Item red_slime = new ItemBase("red_slime");
	public static final Item yellow_slime = new ItemBase("yellow_slime");
	public static final Item purple_slime = new ItemBase("purple_slime");
	public static final Item black_slime = new ItemBase("black_slime");

	public static void registerOreDict() {
		OreDictionary.registerOre("slimeball", blue_slime);
		OreDictionary.registerOre("slimeball", red_slime);
		OreDictionary.registerOre("slimeball", yellow_slime);
		OreDictionary.registerOre("slimeball", purple_slime);
		OreDictionary.registerOre("slimeball", black_slime);
    }
}
