package de.timgoll.facading.init;

import java.util.ArrayList;
import java.util.List;

import de.timgoll.facading.Facading;
import de.timgoll.facading.blocks.*;
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
	public static final Block BLOCK_FACADE            = new BlockFacade("facade");
	public static final Block BLOCK_MACHINEFRAME      = new BlockMachineFrame("machineframe");
	public static final Block BLOCK_FACADINGBENCH     = new BlockFacadingbench("facadingbench");

	//BLOCKITEMS
	public static final Item ITEM_FACADE              = Item.getItemFromBlock(BLOCK_FACADE);
	public static final Item ITEM_MACHINEFRAME        = Item.getItemFromBlock(BLOCK_MACHINEFRAME);
	public static final Item ITEM_FACADINGBENCH       = Item.getItemFromBlock(BLOCK_FACADINGBENCH);

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
