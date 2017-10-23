package de.timgoll.facading.container;

import de.timgoll.facading.container.slots.SlotFacadingbench;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ContainerFacadingbench extends Container {

    private int SLOTSIZE = 18;

    private int SHIFTLEFT = 8;
    private int HOTBARSHIFT = 169;
    private int PINVSHIFT = 111;
    private int INPUTSHIFT = 79;

    private int SHIFTLEFTWIDE = 152;
    private int UNCRAFTSHIFT = 56;
    private int TOOLSHIFT = 19;
    private int SHITLEFTOUTPUT = 98;

    public ContainerFacadingbench(InventoryPlayer inventoryPlayer, TileBlockFacadingbench tileBlockFacadingbench) {
        if (tileBlockFacadingbench.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {

            IItemHandler inventory = tileBlockFacadingbench.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

            /*
             * Container inventory
             */

            // FACADINGBENCH INPUT SLOT
            for (int i = 0; i < 9; i++) {
                addSlotToContainer(new SlotFacadingbench(inventory, i, SHIFTLEFT + (i * SLOTSIZE), INPUTSHIFT, getAllowedItemsInput() ));
            }

            // FACADINGBENCH UNCRAFT SLOT
            addSlotToContainer(new SlotFacadingbench(inventory, 9, SHIFTLEFTWIDE, UNCRAFTSHIFT, getAllowedItemsUncraft() ));

            // FACADINGBENCH TOOL SLOT
            addSlotToContainer(new SlotFacadingbench(inventory, 10, SHIFTLEFTWIDE, TOOLSHIFT, getAllowedItemsTools() ));

            //FACADINGBENCH OUTPUT SLOT
            addSlotToContainer(new SlotFacadingbench(inventory, 11, SHITLEFTOUTPUT, TOOLSHIFT, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 12, SHITLEFTOUTPUT + SLOTSIZE, TOOLSHIFT, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 13, SHITLEFTOUTPUT, TOOLSHIFT + SLOTSIZE, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 14, SHITLEFTOUTPUT + SLOTSIZE, TOOLSHIFT + SLOTSIZE, getAllowedItemsOutput() ));


            /*
             * Vanilla inventory
             */

            // MAIN PLAYER INVENTORY
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    addSlotToContainer(new Slot(inventoryPlayer, x + (y * 9) + 9, SHIFTLEFT + x * SLOTSIZE, PINVSHIFT + y * SLOTSIZE));
                }
            }

            // PLAYER HOTBAR INVENTORY
            for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, SHIFTLEFT + (i * SLOTSIZE), HOTBARSHIFT));
            }
        }
    }

    private List<Item> getAllowedItemsInput() {
        List<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_NAIL);
        allowedItems.add(ModRegistry.ITEM_PILEOFNAILS);
        allowedItems.add(ModRegistry.ITEM_BOXOFNAILS);
        allowedItems.add(ModRegistry.ITEM_FRAMEBUNDLE);

        return allowedItems;
    }

    private List<Item> getAllowedItemsUncraft() {
        List<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_FACADE);

        return allowedItems;
    }

    private List<Item> getAllowedItemsTools() {
        List<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_FLINTWOODCUTTER);
        allowedItems.add(ModRegistry.ITEM_IRONTOOTHSAW);
        allowedItems.add(ModRegistry.ITEM_IRONJAPANSAW);
        allowedItems.add(ModRegistry.ITEM_DIAMONDCIRCULARSAW);

        return allowedItems;
    }

    private List<Item> getAllowedItemsOutput() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(stackInSlot, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stackInSlot, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            slot.onTake(player, stackInSlot);

        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
