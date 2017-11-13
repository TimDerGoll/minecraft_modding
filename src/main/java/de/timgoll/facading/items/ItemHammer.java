package de.timgoll.facading.items;

import de.timgoll.facading.Facading;
import de.timgoll.facading.blocks.BlockFacade;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.titleentities.TileBlockFacade;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

import static de.timgoll.facading.init.ModRegistry.BLOCK_FACADE;

public class ItemHammer extends Item implements IHasModel {

    public ItemHammer(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(Facading.MODID + "." + name);
        this.setCreativeTab(ModRegistry.TAB);
        this.setMaxStackSize(1);

        ModRegistry.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        String tooltip_text = new TextComponentTranslation("tooltip.hammer.text").getFormattedText();
        tooltip.add(tooltip_text);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        //if (world.isRemote)
        //    player.sendMessage(new TextComponentString("You have right clicked with a " + this.getRegistryName()));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (world.getBlockState(pos).getBlock() instanceof BlockFacade) {
                TileBlockFacade tileBlockFacade = (TileBlockFacade) world.getTileEntity(pos);
                if (tileBlockFacade == null)
                    return EnumActionResult.FAIL;

                if (player.isSneaking())
                    tileBlockFacade.transform_reversed(facing);
                else
                    tileBlockFacade.transform(facing);
            }
        }
        return EnumActionResult.SUCCESS;
    }

}