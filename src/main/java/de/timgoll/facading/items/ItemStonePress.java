package de.timgoll.facading.items;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemStonePress extends Item implements IHasModel {

    public ItemStonePress(String name) {
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

}
