package de.timgoll.facading.container;

import de.timgoll.facading.container.slots.SlotFacadingbench;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.misc.CustomRecipeRegistry;
import de.timgoll.facading.misc.RecipeHandlerPress;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.packets.PacketGuiOpened;
import de.timgoll.facading.titleentities.TileBlockMachineBase;
import de.timgoll.facading.titleentities.TileBlockMachinePress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ContainerMachinePress extends ContainerMachineBase {

    private int SLOTSIZE = 18;

    private int SHIFTLEFT = 8;
    private int HOTBARSHIFT = 131;
    private int PINVSHIFT = 73;

    private int TOPSHIFT = 19;
    private int SHITLEFTOUTPUT = 98;
    private int SHIFTLEFTWIDE = 152;

    private int INPUTTOP = 28;
    private int INPUTLEFT = 44;

    public ContainerMachinePress(InventoryPlayer inventoryPlayer, TileBlockMachineBase tileBlockMachineBase) {
        super(inventoryPlayer, tileBlockMachineBase);

        if (tileBlockMachineBase.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
            IItemHandler inventory = tileBlockMachineBase.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

            addSlotToContainer(new SlotFacadingbench(inventory, 0, INPUTLEFT, INPUTTOP, getAllowedItemsInput() ));

            addSlotToContainer(new SlotFacadingbench(inventory, 1, SHIFTLEFTWIDE, TOPSHIFT, getAllowedItemsTools() ));

            //FACADINGBENCH OUTPUT SLOT
            addSlotToContainer(new SlotFacadingbench(inventory, 2, SHITLEFTOUTPUT, TOPSHIFT, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 3, SHITLEFTOUTPUT + SLOTSIZE, TOPSHIFT, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 4, SHITLEFTOUTPUT, TOPSHIFT + SLOTSIZE, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 5, SHITLEFTOUTPUT + SLOTSIZE, TOPSHIFT  + SLOTSIZE, getAllowedItemsOutput() ));

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

        for (ArrayList<ArrayList<ItemStack>> inputStackList : CustomRecipeRegistry.getInputList("press")) {
            for (ItemStack inputStack : inputStackList.get(0))
                allowedItems.add(inputStack.getItem() );
        }

        return allowedItems;
    }

    private List<Item> getAllowedItemsTools() {
        List<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_IRONPRESS);
        allowedItems.add(ModRegistry.ITEM_DIAMONDPRESS);
        allowedItems.add(ModRegistry.ITEM_NETHERPRESS);

        return allowedItems;
    }

    private List<Item> getAllowedItemsOutput() {
        return new ArrayList<>();
    }

    @Override
    public void guiOpened() {
        if (!this.world.isRemote) {
            BlockPos pos = this.tileBlockMachineBase.getPos();

            PacketHandler.INSTANCE.sendTo(
                    new PacketGuiOpened(
                            this.tileBlockMachineBase.getProductionTicks(),
                            this.tileBlockMachineBase.getElapsedTicksProducing(),
                            this.tileBlockMachineBase.getIsPowered(),
                            this.tileBlockMachineBase.getIsProducing(),
                            pos.getX(),
                            pos.getY(),
                            pos.getZ()
                    ),
                    (EntityPlayerMP) this.player
            );
        }
    }
}
