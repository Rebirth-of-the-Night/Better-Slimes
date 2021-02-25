package MICDeps;

import java.io.File;

import com.mic.betterslimes.items.ModItems;

import MICDeps.items.ItemBuilder;
import MICDeps.proxy.CommonProxy;
import MICDeps.util.handlers.ConfigHandler;
import MICDeps.util.handlers.RegistryHandler;
import MICDeps.util.handlers.SoundsHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModBase {

	public static final String CLIENT_PROXY_CLASS = "MICDeps.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "MICDeps.proxy.CommonProxy";

	public static ItemBuilder itemBuilder;
	public static ModItems items;

	public static File config;
	static RegistryHandler eventHandler;

	@Mod.Instance()
	public static ModBase instance;

	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		eventHandler = new RegistryHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
		MinecraftForge.EVENT_BUS.register(itemBuilder);
        config = event.getSuggestedConfigurationFile();
        ConfigHandler.init(config);
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		SoundsHandler.registerSounds();
		proxy.init(event);
	}
}
