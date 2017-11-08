package de.timgoll.facading;

import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.titleentities.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import de.timgoll.facading.proxy.CommonProxy;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(this);
		PROXY.init(e);

		registerTileEntities();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		PROXY.postInit(e);
	}


	//Register TileEntities
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileBlockFacade.class, "tileentity_block_facade");

		GameRegistry.registerTileEntity(TileBlockMachineFacadingbench.class, "tileentity_block_facadingbench");
		GameRegistry.registerTileEntity(TileBlockMachinePress.class, "tileentity_block_press");
		GameRegistry.registerTileEntity(TileBlockMachineNailMaker.class, "tileentity_block_nailmaker");
		GameRegistry.registerTileEntity(TileBlockMachineSawmill.class, "tileentity_block_sawmill");
	}
}