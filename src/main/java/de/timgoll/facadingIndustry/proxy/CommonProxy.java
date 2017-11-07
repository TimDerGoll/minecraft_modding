package de.timgoll.facadingIndustry.proxy;

import de.timgoll.facadingIndustry.FacadingIndustry;
import de.timgoll.facadingIndustry.client.gui.GuiHandler;
import de.timgoll.facadingIndustry.init.ModRegistry;
import de.timgoll.facadingIndustry.misc.RecipeHandler;
import de.timgoll.facadingIndustry.network.PacketHandler;
import de.timgoll.facadingIndustry.titleentities.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public static RecipeHandler RECIPEHANDLER = new RecipeHandler();

	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
		PacketHandler.registerMessages(FacadingIndustry.MODID);
	}

	public void init(FMLInitializationEvent e) {
		RECIPEHANDLER.registerRecipes();
	}

	public void postInit(FMLPostInitializationEvent e) {
		registerTileEntities();

		NetworkRegistry.INSTANCE.registerGuiHandler(FacadingIndustry.INSTANCE, new GuiHandler());
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileBlockFacade.class, "tileentity_block_facade");
		GameRegistry.registerTileEntity(TileBlockMachineFacadingbench.class, "tileentity_block_facadingbench");
		GameRegistry.registerTileEntity(TileBlockPress.class, "tileentity_block_press");
		GameRegistry.registerTileEntity(TileBlockNailMaker.class, "tileentity_block_nailmaker");
		GameRegistry.registerTileEntity(TileBlockSawmill.class, "tileentity_block_sawmill");
	}

}
