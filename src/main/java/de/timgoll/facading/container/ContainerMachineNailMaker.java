package de.timgoll.facading.container;

import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.misc.CustomRecipeRegistry;
import de.timgoll.facading.titleentities.TileBlockMachineNailMaker;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ContainerMachineNailMaker extends ContainerMachineGeneric {

    public ContainerMachineNailMaker(InventoryPlayer inventoryPlayer, TileBlockMachineNailMaker tileBlockMachineNailMaker) {
        super(inventoryPlayer, tileBlockMachineNailMaker);
    }

    @Override
    ArrayList<Item> getAllowedItemsInput() {
        ArrayList<Item> allowedItems = new ArrayList<>();

        for (ArrayList<ArrayList<ItemStack>> inputStackList : CustomRecipeRegistry.getInputList("nailmaker")) {
            for (ItemStack inputStack : inputStackList.get(0))
                allowedItems.add( inputStack.getItem() );
        }

        return allowedItems;
    }

    @Override
    ArrayList<Item> getAllowedItemsTools() {
        ArrayList<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_BOXER);

        return allowedItems;
    }
}
