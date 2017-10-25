package de.timgoll.facading.init;

import java.util.ArrayList;
import java.util.List;

import de.timgoll.facading.Facading;
import de.timgoll.facading.blocks.BlockFacade;
import de.timgoll.facading.blocks.BlockFacadingbench;
import de.timgoll.facading.blocks.BlockFacadingbench_powered;
import de.timgoll.facading.blocks.BlockMachineFrame;
import de.timgoll.facading.items.*;
import de.timgoll.facading.sounds.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.client.audio.Sound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModRegistry {

	public static final List<Block> BLOCKS = new ArrayList<>();
	public static final List<Item> ITEMS = new ArrayList<>();
	
	public static final CreativeTabs TAB = new CreativeTabs(Facading.MODID) {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ITEM_HAMMER);
		}
		
	};


	//BLOCKS
	public static final BlockFacade        BLOCK_FACADE                = new BlockFacade("facade");
	public static final BlockMachineFrame  BLOCK_MACHINEFRAME          = new BlockMachineFrame("machineframe");
	public static final BlockFacadingbench BLOCK_FACADINGBENCH         = new BlockFacadingbench("facadingbench");
	public static final BlockFacadingbench_powered BLOCK_FACADINGBENCH_POWERED = new BlockFacadingbench_powered("facadingbench_powered");

	//BLOCKITEMS
	public static final Item ITEM_FACADE              = BLOCK_FACADE.registerBlockItem();
	public static final Item ITEM_MACHINEFRAME        = BLOCK_MACHINEFRAME.registerBlockItem();
	public static final Item ITEM_FACADINGBENCH       = BLOCK_FACADINGBENCH.registerBlockItem();
	public static final Item ITEM_FACADINGBENCH_POWERED       = BLOCK_FACADINGBENCH_POWERED.registerBlockItem();

	//ITEMS
	public static final Item ITEM_HAMMER              = new ItemHammer("hammer");
	public static final Item ITEM_TOOLHOLDER          = new ItemToolHolder("toolholder");
	public static final Item ITEM_WATERMILL           = new ItemWaterMill("watermill");
	public static final Item ITEM_FRAMEBUNDLE         = new ItemFramebundle("framebundle");
	public static final Item ITEM_REINFORCEMENTBUNDLE = new ItemReinforcementbundle("reinforcementbundle");
	public static final Item ITEM_FLINTWOODCUTTER     = new ItemFlintWoodCutter("flintwoodcutter");
	public static final Item ITEM_IRONTOOTHSAW        = new ItemIronToothSaw("irontoothsaw");
	public static final Item ITEM_DIAMONDCIRCULARSAW  = new ItemDiamondCircularSaw("diamondcircularsaw");
	public static final Item ITEM_NETHERSAW           = new ItemNetherSaw("nethersaw");
	public static final Item ITEM_NAIL                = new ItemNail("nail");
	public static final Item ITEM_PILEOFNAILS         = new ItemPileOfNails("pileofnails");
	public static final Item ITEM_BOXOFNAILS          = new ItemBoxOfNails("boxofnails");
	public static final Item ITEM_STONEPRESS          = new ItemStonePress("stonepress");
	public static final Item ITEM_IRONPRESS           = new ItemIronPress("ironpress");
	public static final Item ITEM_DIAMONDPRESS        = new ItemDiamondPress("diamondpress");

	//SOUNDS
	public static final SoundEvent SOUND_FACADINGBENCH_POWERED = new SoundHandler("facadingbench_powered").getSoundEvent();

	@SubscribeEvent
	public void onBlockRegister(Register<Block> event) {
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public void onItemRegister(Register<Item> event) {
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}

}
