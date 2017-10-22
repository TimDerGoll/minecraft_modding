package de.timgoll.facading.init;

import java.util.ArrayList;
import java.util.List;

import de.timgoll.facading.Facading;
import de.timgoll.facading.blocks.BlockFacade;
import de.timgoll.facading.blocks.BlockFacadingbench;
import de.timgoll.facading.items.ItemHammer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModRegistry {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final CreativeTabs TAB = new CreativeTabs(Facading.MODID) {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ITEM_HAMMER);
		}
		
	};

	//public static final Item TEST = new ItemTest("test");
	//public static final Block TEST_BLOCK = new BlockTest("test_block");
	//public static final Item META_TEST = new ItemMetaTest("test_meta_item");
	//public static final Block STATE_TEST = new BlockWithStates("test_state_block");


	//BLOCKS
	public static final Block BLOCK_FACADE        = new BlockFacade("facade");
	public static final Block BLOCK_FACADINGBENCH = new BlockFacadingbench("facadingbench");

	//ITEMS
	public static final Item ITEM_HAMMER          = new ItemHammer("hammer");

	@SubscribeEvent
	public void onBlockRegister(Register<Block> event) {
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public void onItemRegister(Register<Item> event) {
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}

}
