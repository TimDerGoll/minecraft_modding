package de.timgoll.facadingIndustry.items;

import de.timgoll.facadingIndustry.FacadingIndustry;
import de.timgoll.facadingIndustry.client.IHasModel;
import de.timgoll.facadingIndustry.init.ModRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemFramebundle extends Item implements IHasModel {

    public ItemFramebundle(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(FacadingIndustry.MODID + "." + name);
        this.setCreativeTab(ModRegistry.TAB);

        ModRegistry.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
