package de.timgoll.facading.container;

import de.timgoll.facading.container.slots.SlotFacadingbench;
import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.packets.PacketGuiOpened;
import de.timgoll.facading.titleentities.TileBlockMachineBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ContainerMachineFacadingbench extends ContainerMachineBase {

    private int SLOTSIZE = 18;

    private int SHIFTLEFT = 8;
    private int HOTBARSHIFT = 169;
    private int PINVSHIFT = 111;
    private int INPUTSHIFT = 79;

    private int SHIFTLEFTWIDE = 152;
    private int UNCRAFTSHIFT = 56;
    private int TOPSHIFT = 19;
    private int SHITLEFTOUTPUT = 98;

    public ContainerMachineFacadingbench(InventoryPlayer inventoryPlayer, TileBlockMachineBase tileBlockMachineBase) {
        super(inventoryPlayer, tileBlockMachineBase);

        if (tileBlockMachineBase.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {

            IItemHandler inventory = tileBlockMachineBase.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

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
            addSlotToContainer(new SlotFacadingbench(inventory, 10, SHIFTLEFTWIDE, TOPSHIFT, getAllowedItemsTools() ));

            //FACADINGBENCH OUTPUT SLOT
            addSlotToContainer(new SlotFacadingbench(inventory, 11, SHITLEFTOUTPUT, TOPSHIFT, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 12, SHITLEFTOUTPUT + SLOTSIZE, TOPSHIFT, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 13, SHITLEFTOUTPUT, TOPSHIFT + SLOTSIZE, getAllowedItemsOutput() ));
            addSlotToContainer(new SlotFacadingbench(inventory, 14, SHITLEFTOUTPUT + SLOTSIZE, TOPSHIFT + SLOTSIZE, getAllowedItemsOutput() ));


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

        allowedItems.add(ModRegistry.ITEM_BOXOFNAILS);
        allowedItems.add(ModRegistry.ITEM_FRAMEBUNDLE);
        allowedItems.add(ModRegistry.ITEM_REINFORCEMENTBUNDLE);

        return allowedItems;
    }

    private List<Item> getAllowedItemsUncraft() {
        List<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_FACADE);

        return allowedItems;
    }

    private List<Item> getAllowedItemsTools() {
        List<Item> allowedItems = new ArrayList<>();

        allowedItems.add(ModRegistry.ITEM_IRONTOOTHSAW);
        allowedItems.add(ModRegistry.ITEM_DIAMONDCIRCULARSAW);
        allowedItems.add(ModRegistry.ITEM_NETHERSAW);

        return allowedItems;
    }

    private List<Item> getAllowedItemsOutput() {
        return new ArrayList<>();
    }

    @Override
    public void guiOpened() {
        if (!this.world.isRemote) {
            BlockPos pos = this.tileBlockMachine.getPos();

            PacketHandler.INSTANCE.sendTo(
                    new PacketGuiOpened(
                            this.tileBlockMachine.getProductionTicks(),
                            this.tileBlockMachine.getElapsedTicksProducing(),
                            this.tileBlockMachine.getDisassembleTicks(),
                            this.tileBlockMachine.getElapsedDisassembleTicks(),
                            this.tileBlockMachine.getOutputBlocks_amount(),
                            this.tileBlockMachine.getOutputBlocks_indexProducing(),
                            this.tileBlockMachine.getIsPowered(),
                            this.tileBlockMachine.getIsProducing(),
                            this.tileBlockMachine.getIsDisassembling(),
                            pos.getX(),
                            pos.getY(),
                            pos.getZ()
                    ),
                    (EntityPlayerMP) this.player
            );
        }
    }
}
