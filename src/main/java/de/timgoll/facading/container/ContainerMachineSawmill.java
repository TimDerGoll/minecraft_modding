package de.timgoll.facading.container;

import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.misc.CustomRecipeRegistry;
import de.timgoll.facading.titleentities.TileBlockMachineSawmill;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ContainerMachineSawmill extends ContainerMachineGeneric {

    public ContainerMachineSawmill(InventoryPlayer inventoryPlayer, TileBlockMachineSawmill tileBlockMachineSawmill) {
        super(inventoryPlayer, tileBlockMachineSawmill);
    }

    @Override
    ArrayList<Item> getAllowedItemsInput() {
        ArrayList<Item> allowedItems = new ArrayList<>();

        for (ArrayList<ArrayList<ItemStack>> inputStackList : CustomRecipeRegistry.getInputList("sawmill")) {
            for (ItemStack inputStack : inputStackList.get(0))
                allowedItems.add( inputStack.getItem() );
        }

        return allowedItems;
    }

    @Override
    ArrayList<Item> getAllowedItemsTools() {
        ArrayList<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_IRONTOOTHSAW);
        allowedItems.add(ModRegistry.ITEM_DIAMONDCIRCULARSAW);
        allowedItems.add(ModRegistry.ITEM_NETHERSAW);

        return allowedItems;
    }
}
