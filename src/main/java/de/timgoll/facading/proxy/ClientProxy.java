package de.timgoll.facading.proxy;

import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.client.gui.GuiMachineBase;
import de.timgoll.facading.client.gui.GuiMachineFacadingbench;
import de.timgoll.facading.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(GuiMachineBase.class); //Tickingevent
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);

	}

	@SubscribeEvent
	public void onModelRegister(ModelRegistryEvent e) {
		for (Block b : ModRegistry.BLOCKS)
			if (b instanceof IHasModel)
				((IHasModel) b).registerModels();
		for (Item i : ModRegistry.ITEMS)
			if (i instanceof IHasModel)
				((IHasModel) i).registerModels();
	}

}
