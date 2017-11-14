package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.titleentities.TileBlockFacade;
import de.timgoll.facading.titleentities.TileBlockMachineBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockFacade extends Block implements IHasModel, ITileEntityProvider {

    private static boolean keepInventory;
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST  = PropertyBool.create("west");
    private static final PropertyBool EAST  = PropertyBool.create("east");
    private static final PropertyBool UP    = PropertyBool.create("up");
    private static final PropertyBool DOWN  = PropertyBool.create("down");

    public BlockFacade(String name) {
        super(Material.WOOD);
        this.setHardness(0.1F);
        this.setSoundType(SoundType.WOOD);
        this.setRegistryName(name);
        this.setUnlocalizedName(Facading.MODID + "." + name);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false)
                .withProperty(EAST, false)
                .withProperty(UP, false)
                .withProperty(DOWN, false)
        );

        registerBlock();

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


    public static void setState(World world, BlockPos pos) {
        TileBlockFacade te = (TileBlockFacade) world.getTileEntity(pos);

        if (te == null)
            return;

        keepInventory = true; //set keepInventory to true, so that the TE stores the data without dropping inv
        world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState()
                        .withProperty(NORTH, te.north())
                        .withProperty(SOUTH, te.south())
                        .withProperty(WEST, te.west())
                        .withProperty(EAST, te.east())
                        .withProperty(UP, te.up())
                        .withProperty(DOWN, te.down())
                , 6);
        keepInventory = false;

        world.markBlockRangeForRenderUpdate(pos, pos);
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        this.getDefaultState()
                .withProperty(NORTH, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false)
                .withProperty(EAST, false)
                .withProperty(UP, false)
                .withProperty(DOWN, false);
    }
    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileBlockFacade te = (TileBlockFacade) world.getTileEntity(pos);

        if (te == null)
            return;

        world.setBlockState(pos, state
                        .withProperty(NORTH, te.north())
                        .withProperty(SOUTH, te.south())
                        .withProperty(WEST, te.west())
                        .withProperty(EAST, te.east())
                        .withProperty(UP, te.up())
                        .withProperty(DOWN, te.down())
                , 6);
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(
                state.getProperties().get(EAST).equals(true) ? 0.5D : 1.0D,
                state.getProperties().get(UP).equals(true) ? 0.5D : 1.0D,
                state.getProperties().get(SOUTH).equals(true) ? 0.5D : 1.0D,
                state.getProperties().get(WEST).equals(true) ? 0.5D : 0.0D,
                state.getProperties().get(DOWN).equals(true) ? 0.5D : 0.0D,
                state.getProperties().get(NORTH).equals(true) ? 0.5D : 0.0D
        );
    }

    /**
     * when the block is placed, set the appropriate facing direction based on which way the player is looking
     */
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        TileBlockFacade te = (TileBlockFacade) world.getTileEntity(pos);

        if (te == null)
            return this.getDefaultState()
                    .withProperty(NORTH, false)
                    .withProperty(SOUTH, false)
                    .withProperty(WEST, false)
                    .withProperty(EAST, false)
                    .withProperty(UP, false)
                    .withProperty(DOWN, false);
        else
            return this.getDefaultState()
                    .withProperty(NORTH, te.north())
                    .withProperty(SOUTH, te.south())
                    .withProperty(WEST, te.west())
                    .withProperty(EAST, te.east())
                    .withProperty(UP, te.up())
                    .withProperty(DOWN, te.down());
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileBlockFacade te = (TileBlockFacade) world.getTileEntity(pos);
        if (te == null)
            return state
                    .withProperty(NORTH, true)
                    .withProperty(SOUTH, false)
                    .withProperty(WEST, false)
                    .withProperty(EAST, false)
                    .withProperty(UP, false)
                    .withProperty(DOWN, false);
        else
            return state
                    .withProperty(NORTH, te.north())
                    .withProperty(SOUTH, te.south())
                    .withProperty(WEST, te.west())
                    .withProperty(EAST, te.east())
                    .withProperty(UP, te.up())
                    .withProperty(DOWN, te.down());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state
                .withProperty(NORTH, state.getValue(NORTH))
                .withProperty(SOUTH, state.getValue(SOUTH))
                .withProperty(WEST, state.getValue(WEST))
                .withProperty(EAST, state.getValue(EAST))
                .withProperty(UP, state.getValue(UP))
                .withProperty(DOWN, state.getValue(DOWN));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state
                .withProperty(NORTH, state.getValue(NORTH))
                .withProperty(SOUTH, state.getValue(SOUTH))
                .withProperty(WEST, state.getValue(WEST))
                .withProperty(EAST, state.getValue(EAST))
                .withProperty(UP, state.getValue(UP))
                .withProperty(DOWN, state.getValue(DOWN));
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileBlockFacade te = (TileBlockFacade) world.getTileEntity(pos);

            if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
                IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

                if (inventory != null) {
                    for (int i = 0; i < inventory.getSlots(); i++) {
                        if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i));

                            float multiplier = 0.1f;
                            float motionX = world.rand.nextFloat() - 0.5f;
                            float motionY = world.rand.nextFloat() - 0.5f;
                            float motionZ = world.rand.nextFloat() - 0.5f;

                            item.motionX = motionX * multiplier;
                            item.motionY = motionY * multiplier;
                            item.motionZ = motionZ * multiplier;

                            world.spawnEntity(item);
                        }
                    }
                }
            }
            world.removeTileEntity(pos);
        }
        super.breakBlock(world, pos, state);
    }








    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
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

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemInMainHand = player.inventory.getCurrentItem();
        boolean isBlock          = itemInMainHand.getItem() instanceof ItemBlock;
        boolean isFacade         = isBlock && itemInMainHand.getItem().getRegistryName().equals( this.getRegistryName() );

        if (world.isRemote && isBlock && !isFacade) {
            player.sendMessage(new TextComponentString("You have changed to " + itemInMainHand.getDisplayName() ));
        }

        //return true --> no block gets placed
        return !isFacade && isBlock;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlockFacade();
    }


    /**
     * Make a glass effect, sides to adjacent blocks don't get rendered
     */
    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (this == ModRegistry.BLOCK_FACADE) {
            if (blockState != iblockstate)
                return true;

            if (block == this)
                return false;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    /**
     * Register Block-State for rotation
     */
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {NORTH, SOUTH, EAST, WEST, UP, DOWN});
    }
}
