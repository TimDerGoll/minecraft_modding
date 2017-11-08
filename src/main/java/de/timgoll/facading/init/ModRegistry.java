package de.timgoll.facading.init;

import java.util.ArrayList;
import java.util.List;

import de.timgoll.facading.Facading;
import de.timgoll.facading.blocks.*;
import de.timgoll.facading.items.*;
import de.timgoll.facading.sounds.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
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
	public static final BlockMachineFrame         BLOCK_MACHINEFRAME   = new BlockMachineFrame("machineframe");
	public static final BlockMachineFacadingbench BLOCK_FACADINGBENCH  = new BlockMachineFacadingbench("facadingbench");
	public static final BlockPress                BLOCK_PRESS          = new BlockPress("press");

	public static final BlockFacade               BLOCK_FACADE         = new BlockFacade("facade");

	public static final BlockLampFlat             BLOCK_LAMPFLAT       = new BlockLampFlat("flatlamp");
	public static final BlockLampIndustrial       BLOCK_LAMPINDUSTRIAL = new BlockLampIndustrial("industriallamp");

	//BLOCKITEMS
	public static final Item ITEM_MACHINEFRAME          = BLOCK_MACHINEFRAME.registerBlockItem();
	public static final Item ITEM_FACADINGBENCH         = BLOCK_FACADINGBENCH.registerBlockItem();
	public static final Item ITEM_PRESS                 = BLOCK_PRESS.registerBlockItem();

	public static final Item ITEM_FACADE                = BLOCK_FACADE.registerBlockItem();

	public static final Item ITEM_LAMPFLAT              = BLOCK_LAMPFLAT.registerBlockItem();
	public static final Item ITEM_LAMPINDUSTRIAL        = BLOCK_LAMPINDUSTRIAL.registerBlockItem();

	//ITEMS
	public static final Item ITEM_FRAMEBUNDLE           = new ItemDefault("framebundle");
	public static final Item ITEM_REINFORCEMENTBUNDLE   = new ItemDefault("reinforcementbundle");
	public static final Item ITEM_NAIL                  = new ItemDefault("nail");
	public static final Item ITEM_PILEOFNAILS           = new ItemDefault("pileofnails");
	public static final Item ITEM_BOXOFNAILS            = new ItemDefault("boxofnails");
	public static final Item ITEM_TOOLHOLDER            = new ItemDefault("toolholder");
	public static final Item ITEM_WATERMILL             = new ItemDefault("watermill");
	public static final Item ITEM_DIAMOND_PLATE         = new ItemDefault("diamond_plate");
	public static final Item ITEM_EMERALD_PLATE         = new ItemDefault("emerald_plate");
	public static final Item ITEM_UNCOMPRESSED_DEMERALD = new ItemDefault("uncompressed_demerald");
	public static final Item ITEM_DEMERALD              = new ItemDefault("demerald");

	public static final Item ITEM_HAMMER                = new ItemHammer("hammer");

	public static final Item ITEM_BOXER                 = new ItemBoxer("boxer");
	public static final Item ITEM_IRONTOOTHSAW          = new ItemIronToothSaw("irontoothsaw");
	public static final Item ITEM_DIAMONDCIRCULARSAW    = new ItemDiamondCircularSaw("diamondcircularsaw");
	public static final Item ITEM_NETHERSAW             = new ItemNetherSaw("nethersaw");
	public static final Item ITEM_IRONPRESS             = new ItemIronPress("ironpress");
	public static final Item ITEM_DIAMONDPRESS          = new ItemDiamondPress("diamondpress");
	public static final Item ITEM_NETHERPRESS           = new ItemNetherPress("netherpress");

	//TOOLS
	public static Item.ToolMaterial TM_DEMERALD         = EnumHelper.addToolMaterial("tm_demerald", 3, 3500, 10F, 4.5F, 16);
	public static final Item DEMERALD_PICKAXE           = new ItemToolDemeraldPickaxe("demerald_pickaxe");
	public static final Item DEMERALD_SHOVEL            = new ItemToolDemeraldShovel("demerald_shovel");
	//public static final Item DEMERALD_AXE               = new ItemToolDemeraldAxe("demerald_axe");
	public static final Item DEMERALD_HOE               = new ItemToolDemeraldHoe("demerald_hoe");
	public static final Item DEMERALD_SWORD             = new ItemToolDemeraldSword("demerald_sword");

	//SOUNDS
	public static final SoundEvent SOUND_MACHINE_POWERED = new SoundHandler("machine_powered").getSoundEvent();

	@SubscribeEvent
	public void onBlockRegister(Register<Block> event) {
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public void onItemRegister(Register<Item> event) {
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}

}
