package de.timgoll.facadingIndustry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import de.timgoll.facadingIndustry.proxy.CommonProxy;

@Mod(modid = FacadingIndustry.MODID, name = FacadingIndustry.NAME, version = FacadingIndustry.VERSION)
public class FacadingIndustry {

	public static final String MODID = "facadingIndustry";
	public static final String NAME = "Facading Industry";
	public static final String VERSION = "alpha_0.5";

	@Instance
	public static FacadingIndustry INSTANCE;

	@SidedProxy(clientSide = "de.timgoll.facadingIndustry.proxy.ClientProxy", serverSide = "de.timgoll.facadingIndustry.proxy.CommonProxy")
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
