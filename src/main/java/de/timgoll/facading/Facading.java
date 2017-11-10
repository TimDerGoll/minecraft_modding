package de.timgoll.facading;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import de.timgoll.facading.proxy.CommonProxy;

@Mod(modid = Facading.MODID, name = Facading.NAME, version = Facading.VERSION)
public class Facading {

	public static final String MODID = "facading";
	public static final String NAME = "Facading Industry";
	public static final String VERSION = "alpha_0.5";

	@Instance
	public static Facading INSTANCE;

	@SidedProxy(clientSide = "de.timgoll.facading.proxy.ClientProxy", serverSide = "de.timgoll.facading.proxy.CommonProxy")
	public static CommonProxy PROXY;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		PROXY.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
		PROXY.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		PROXY.postInit(e);
	}
}
