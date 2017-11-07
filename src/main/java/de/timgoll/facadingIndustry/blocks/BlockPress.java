package de.timgoll.facadingIndustry.blocks;

import de.timgoll.facadingIndustry.titleentities.TileBlockPress;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPress extends BlockMachineBase {

    public BlockPress(String name) {
        super(name);
    }




    /**
     * Gets called, when a Player rightclicks the Block
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //TileBlockMachineFacadingbench te = (TileBlockMachineFacadingbench) world.getTileEntity(pos);

        //if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) { //North is just a placeholder, direction irrelevant
        //    player.openGui(FacadingIndustry.INSTANCE, GuiHandler.GUI_FACADING_CONTAINER_ID, world, pos.getX(), pos.getY(), pos.getZ());
        //}

        return true;
    }

    /**
     * Register the Tile-Entity
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlockPress();
    }

}
