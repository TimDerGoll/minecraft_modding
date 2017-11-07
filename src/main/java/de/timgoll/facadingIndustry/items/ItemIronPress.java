package de.timgoll.facadingIndustry.items;

import de.timgoll.facadingIndustry.FacadingIndustry;
import de.timgoll.facadingIndustry.client.IHasModel;
import de.timgoll.facadingIndustry.init.ModRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;
import java.util.List;

public class ItemIronPress extends Item implements IHasModel {

    public ItemIronPress(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(FacadingIndustry.MODID + "." + name);
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
        String tooltip_text = new TextComponentTranslation("tooltip.upgrade_iron.text").getFormattedText();
        tooltip.add(tooltip_text);
    }

    public int getMultiplier() {
        return 10;
    }

}
