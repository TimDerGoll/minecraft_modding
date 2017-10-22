package de.timgoll.facading.proxy;

import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.misc.RecipeHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public static RecipeHandler RECIPEHANDLER = new RecipeHandler();

	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
	}

	public void init(FMLInitializationEvent e) {
		RECIPEHANDLER.registerRecipes();
	}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
