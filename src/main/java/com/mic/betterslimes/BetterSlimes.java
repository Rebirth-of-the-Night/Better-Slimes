package com.mic.betterslimes;

import com.mic.betterslimes.entity.EntityInit;
import com.mic.betterslimes.handlers.ConfigHandler;
import com.mic.betterslimes.handlers.RegistryHandler;
import com.mic.betterslimes.items.ModItems;
import com.mic.betterslimes.proxy.IProxy;
import com.mic.betterslimes.util.Reference;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class BetterSlimes {

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
        
		EntityInit.registerEntities();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Init sounds
        ModItems.registerOreDict();
		proxy.init(event);
    }
}
