package de.timgoll.facadingIndustry.items;

import com.google.common.collect.Sets;
import de.timgoll.facadingIndustry.FacadingIndustry;
import de.timgoll.facadingIndustry.client.IHasModel;
import de.timgoll.facadingIndustry.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Set;

public class ItemToolDemeraldPickaxe extends ItemTool implements IHasModel {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);

    public ItemToolDemeraldPickaxe(String name) {
        super(ModRegistry.TM_DEMERALD, EFFECTIVE_ON);

        this.setRegistryName(name);
        this.setUnlocalizedName(FacadingIndustry.MODID + "." + name);
        this.setCreativeTab(ModRegistry.TAB);

        ModRegistry.ITEMS.add(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return super.getToolClasses(stack);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
