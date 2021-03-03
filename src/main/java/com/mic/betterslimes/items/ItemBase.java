package com.mic.betterslimes.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item{
	
	/**
	 * Creates Item with name, assigns to misc. item tab.
	 * @param name
	 */
	public ItemBase(String name) {
		this(name, CreativeTabs.MISC);
	}
	
	/**
	 * Creates Item with name & assigns a creative tab.
	 * @param name
	 * @param tab
	 */
	public ItemBase(String name, CreativeTabs tab) {
		setUnlocalizedName(name);
		setRegistryName(name);
		
		setCreativeTab(tab);
		ModItems.MOD_ITEMS.add(this);
	}
}
