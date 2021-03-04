package com.mic.betterslimes;

import com.mic.betterslimes.entity.ModEntities;
import com.mic.betterslimes.util.ModConfig;
import com.mic.betterslimes.items.ModItems;
import com.mic.betterslimes.proxy.IProxy;
import com.mic.betterslimes.util.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class BetterSlimes {
    
    @Mod.Instance
    public static BetterSlimes instance;

	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerRenders();
        ModConfig.init(event.getSuggestedConfigurationFile());
        proxy.preInit(event);
        
		ModEntities.registerEntities();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Init sounds
        ModItems.registerOreDict();
		proxy.init(event);
    }
}
