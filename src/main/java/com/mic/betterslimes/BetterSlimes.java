package com.mic.betterslimes;

import MICDeps.Reference;
import MICDeps.util.handlers.ConfigHandler;
import MICDeps.util.handlers.RegistryHandler;
import MICDeps.util.handlers.SoundsHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mic.betterslimes.entity.EntityInit;
import com.mic.betterslimes.items.ModItems;

import MICDeps.proxy.IProxy;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class BetterSlimes {
	public static ResourceLocation blueSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"blue_slime"));
	public static ResourceLocation redSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"red_slime"));
	public static ResourceLocation blackSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"black_slime"));
	public static ResourceLocation yellowSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"yellow_slime"));
	public static ResourceLocation purpleSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"purple_slime"));
	public static ResourceLocation skySlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"sky_slime"));
	public static ResourceLocation quazarLT = LootTableList.register(new ResourceLocation(Reference.MODID,"quazar"));
	public static ResourceLocation ironSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"iron_slime"));
	public static ResourceLocation goldSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"gold_slime"));
	public static ResourceLocation iceSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"ice_slime"));
	public static ResourceLocation jungleSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"jungle_slime"));
	public static ResourceLocation sandSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"sand_slime"));
	public static ResourceLocation knightSlimeLT = LootTableList.register(new ResourceLocation(Reference.MODID,"knight_slime"));
    public static Map<String, ResourceLocation> slimeLTs = new HashMap<>();

	public static ModItems items;

	public static File config;
    static RegistryHandler eventHandler;
    
    @Mod.Instance
    public static BetterSlimes instance;

	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		items = new ModItems();
		proxy.registerRenders();

		eventHandler = new RegistryHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
        config = event.getSuggestedConfigurationFile();
        ConfigHandler.init(config);
        proxy.preInit(event);
        
		EntityInit.registerEntity();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Init sounds
		SoundsHandler.registerSounds();
        ModItems.registerOreDict();
		proxy.init(event);
    }
}
