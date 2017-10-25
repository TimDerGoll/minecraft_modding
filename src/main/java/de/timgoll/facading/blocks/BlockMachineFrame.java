package de.timgoll.facading.blocks;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.IHasModel;
import de.timgoll.facading.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMachineFrame extends Block implements IHasModel {

    public BlockMachineFrame(String name) {
        super(Material.ROCK);
        this.setHardness(0.05F);

        this.setUnlocalizedName(Facading.MODID + "." + name);
        this.setRegistryName(name);
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

    /**
     * register default generated item for Block
     */
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
