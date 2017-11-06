package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.titleentities.TileBlockMachineFacadingbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class BlockMachineFacadingbench extends BlockMachineBase {

    public BlockMachineFacadingbench(String name) {
        super(name);
    }




    /**
     * Gets called, when a Player rightclicks the Block
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileBlockMachineFacadingbench te = (TileBlockMachineFacadingbench) world.getTileEntity(pos);

            if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) { //North is just a placeholder, direction irrelevant
                player.openGui(Facading.INSTANCE, GuiHandler.GUI_FACADING_CONTAINER_ID, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    /**
     * Register the Tile-Entity
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlockMachineFacadingbench();
    }

}
