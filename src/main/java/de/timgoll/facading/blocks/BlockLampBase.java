package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockLampBase extends Block implements IHasModel {

    public static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockLampBase(String name) {
        super(Material.IRON);
        this.setHardness(2.5F);
        this.setRegistryName(name);
        this.setUnlocalizedName(Facading.MODID + "." + name);
        this.setLightLevel(1.0F);

        registerBlock();

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        this.setCreativeTab(ModRegistry.TAB);
    }

    private void registerBlock() {
        ModRegistry.BLOCKS.add(this);
    }

    public Item registerBlockItem() {
        Item newItemBlock = new ItemBlock(this).setRegistryName(getRegistryName());
        ModRegistry.ITEMS.add(newItemBlock);

        return newItemBlock;
    }

    //register default generated item for Block
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    // used by the renderer to control lighting and visibility of other blocks.
    // set to false because this block doesn't fill the entire 1x1x1 space
    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    // used by the renderer to control lighting and visibility of other blocks, also by
    // (eg) wall or fence to control whether the fence joins itself to this block
    // set to false because this block doesn't fill the entire 1x1x1 space
    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState iBlockState) {
        return false;
    }

    // Remove Hitbox of Block
    @SuppressWarnings("deprecation")
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }



    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        this.setDefaultDirection(world, pos, state);
    }

    private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 1);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ( (EnumFacing) state.getValue(FACING)).getIndex();
    }

    /**
     * when the block is placed, set the appropriate facing direction based on which way the player is looking
     */
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.canPlaceAt(world, pos, facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        else
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (this.canPlaceAt(world, pos, enumfacing))
                {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Register Block-State for rotation
     */
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }





    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.checkForDrop(worldIn, pos, state)) {
            return true;
        }
        else {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            BlockPos blockpos = pos.offset(enumfacing1);
            boolean flag = false;

            if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
                flag = true;
            }
            else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos)) {
                flag = true;
            }

            if (flag) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
                return true;
            }
            else {
                return false;
            }
        }
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING))) {
            return true;
        }
        else {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        return !state.getBlock().equals(Blocks.AIR);
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : FACING.getAllowedValues()) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return true;
            }
        }

        return false;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos)) {
            return true;
        }
        else if (facing != EnumFacing.UP) {
            return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
        }
        else {
            return false;
        }
    }

}
