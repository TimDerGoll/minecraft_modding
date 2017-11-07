package de.timgoll.facading.container.slots;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SlotFacadingbench extends SlotItemHandler {

    private List<Item> ALLOWED_ITEMS = new ArrayList<>();

    public SlotFacadingbench(IItemHandler itemHandler, int index, int xPosition, int yPosition, List<Item> allowed_items) {
        super(itemHandler, index, xPosition, yPosition);

        ALLOWED_ITEMS = allowed_items;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if ( ALLOWED_ITEMS.contains(stack.getItem()) )
            return true;
        else
            return false;
    }

}
