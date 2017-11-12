package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.misc.EnumHandler;
import de.timgoll.facading.titleentities.TileBlockMachineBase;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMachineBase extends Block implements IHasModel, ITileEntityProvider {

    protected static final PropertyDirection FACING = PropertyDirection.create("facing");
    protected static final PropertyEnum      TYPE   = PropertyEnum.create("type", EnumHandler.MachineStates.class);

    protected static boolean keepInventory;

    public BlockMachineBase(String name) {
        super(Material.IRON);
        this.setHardness(3.5F);

        this.setUnlocalizedName(Facading.MODID + "." + name);
        this.setRegistryName(name);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumHandler.MachineStates.DEFAULT));

        //registering
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

    /**
     * register default generated item for Block
     */
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }





    public static void setState(World world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos);
        TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

        if (te == null)
            return;

        keepInventory = true; //set keepInventory to true, so that the TE stores the data without dropping inv
        world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState().withProperty(FACING, iblockstate.getValue((FACING))).withProperty(TYPE, te.getType()), 2);
        keepInventory = false;
    }



    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

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
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

        if (te == null)
            return;

        if (te.getType() == EnumHandler.MachineStates.DEFAULT)
            return;

        EnumFacing enumfacing = state.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;

        //play working sound
        world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, ModRegistry.SOUND_MACHINE_POWERED, SoundCategory.BLOCKS, 0.075F, 0.5F, false);


        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + 1.0D, d2 + d4, 0.0D, 0.1D, 0.0D);

        switch (enumfacing) {
            case WEST:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + 0.75D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD,  d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,  d0 - 0.75D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case NORTH:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + d4, d1, d2 + 0.75D, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + d4, d1, d2 - 0.75D, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(world, pos, state);
    }

    private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            IBlockState iblockstate  = world.getBlockState(pos.north());
            IBlockState iblockstate1 = world.getBlockState(pos.south());
            IBlockState iblockstate2 = world.getBlockState(pos.west());
            IBlockState iblockstate3 = world.getBlockState(pos.east());

            EnumFacing enumfacing    = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH; }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST; }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

            if (te == null)
                world.setBlockState(pos, state.withProperty(FACING, enumfacing), 1);
            else
                world.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(TYPE, te.getType()), 2);
        }
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

        if (te == null)
            world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 1);
        else
            world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, te.getType()), 2);

        detectWaterBlock(placer.getHorizontalFacing().getOpposite().getDirectionVec(), world, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

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
        TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

        if (te == null)
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        else
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, te.getType());
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);

        if (te != null)
            return state.withProperty(TYPE, te.getType());
        else
            return state.withProperty(TYPE, EnumHandler.MachineStates.DEFAULT);
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING))).withProperty(TYPE, state.getValue(TYPE));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("deprecation")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING))).withProperty(TYPE, state.getValue(TYPE));
    }

    /**
     * Register Block-State for rotation
     */
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, TYPE});
    }

    /**
     * Register the Tile-Entity
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    /**
     * detect adjacent blockchanges to detect waterpower
     */
    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        detectWaterBlock(state.getValue(FACING).getDirectionVec(), world, pos);

        super.neighborChanged(state, world, pos, block, fromPos);
    }

    private void detectWaterBlock(Vec3i facingvector, World world, BlockPos pos) {
        if (world.isRemote) //just check on server, abort
            return;

        Block adjacentBlock = world.getBlockState(pos.subtract( facingvector ) ).getBlock(); //substract facing vector, because facing is to the front

        //TODO: Detect if not sourceblock
        //TODO: get water flow vector

        //Detect activation and store value in TileEntity
        TileBlockMachineBase te = (TileBlockMachineBase) world.getTileEntity(pos);
        if (te != null) {
            boolean waterSource = adjacentBlock.equals(Blocks.WATER) || adjacentBlock.equals(Blocks.FLOWING_WATER);

            te.setIsPowered(waterSource); //true or false
        }
    }

}
