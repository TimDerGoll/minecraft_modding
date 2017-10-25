package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.titleentities.TileBlockFacade;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockFacade extends Block implements IHasModel, ITileEntityProvider {
    public BlockFacade(String name) {
        super(Material.WOOD);
        this.setHardness(0.1F);
        this.setSoundType(SoundType.WOOD);
        this.setRegistryName(name);
        this.setUnlocalizedName(Facading.MODID + "." + name);
        this.setCreativeTab(ModRegistry.TAB);

        registerBlock();
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

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        TileBlockFacade tileBlockFacade = (TileBlockFacade) world.getTileEntity(pos);
        player.sendMessage(new TextComponentString( pos.toString() ));
        player.sendMessage(new TextComponentString("BLOCK - now isSlab: " + tileBlockFacade.isSlab));
        if (tileBlockFacade.isSlab) {
            this.setHardness(2.5F);
        } else {
            this.setHardness(0.1F);
        }

        super.onBlockClicked(world, pos, player);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlockFacade();
    }

    @Override
    public void breakBlock (World world, BlockPos pos, IBlockState state) {
        //remove Tileentity, when block is destroyed
        world.removeTileEntity(pos);
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
}
