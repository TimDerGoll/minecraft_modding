package de.timgoll.facading.proxy;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.misc.RecipeHandler;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.titleentities.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	private static final RecipeHandler RECIPEHANDLER = new RecipeHandler();

	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
		PacketHandler.registerMessages(Facading.MODID);
	}

	public void init(FMLInitializationEvent e) {
		RECIPEHANDLER.registerRecipes();
		NetworkRegistry.INSTANCE.registerGuiHandler(Facading.INSTANCE, new GuiHandler());
		registerTileEntities();
	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	//Register TileEntities
	private void registerTileEntities() {
		GameRegistry.registerTileEntity(TileBlockFacade.class, "tileentity_block_facade");

		GameRegistry.registerTileEntity(TileBlockMachineFacadingbench.class, "tileentity_block_facadingbench");
		GameRegistry.registerTileEntity(TileBlockMachinePress.class, "tileentity_block_press");
		GameRegistry.registerTileEntity(TileBlockMachineNailMaker.class, "tileentity_block_nailmaker");
		GameRegistry.registerTileEntity(TileBlockMachineSawmill.class, "tileentity_block_sawmill");
	}

}
