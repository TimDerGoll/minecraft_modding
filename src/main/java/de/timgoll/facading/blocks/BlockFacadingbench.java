package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFacadingbench extends BlockMachineBase {

    public BlockFacadingbench(String name) {
        super(name);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModRegistry.BLOCK_FACADINGBENCH);
    }

    //@Override
    //public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    //    drops.add(new ItemStack( Item.getItemFromBlock(ModRegistry.BLOCK_FACADINGBENCH) ));
    //}

    public static void setState(boolean powered, boolean working, World world, BlockPos pos) {

        IBlockState iblockstate = world.getBlockState(pos);
        TileEntity tileentity   = world.getTileEntity(pos);

        keepInventory = true; //set keepInventory to true, so that the TE stores the data without dropping inv

        if (working) {
            //world.setBlockState(pos, ModRegistry.BLOCK_FACADINGBENCH_WORKING.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else if (powered) {
            world.setBlockState(pos, ModRegistry.BLOCK_FACADINGBENCH_POWERED.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else {
            world.setBlockState(pos, ModRegistry.BLOCK_FACADINGBENCH.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    /**
     * Gets called, when a Player rightclicks the Block
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileBlockFacadingbench te = (TileBlockFacadingbench) world.getTileEntity(pos);
        if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) { //North is just a placeholder, direction irrelevant
            player.openGui(Facading.INSTANCE, GuiHandler.GUI_FACADING_CONTAINER_ID, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    /**
     * Register the Tile-Entity
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlockFacadingbench();
    }

}
