package de.timgoll.facading.container;

import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.facadingbench.PacketGuiAddProduction;
import de.timgoll.facading.network.facadingbench.PacketGuiCancelProduction;
import de.timgoll.facading.network.facadingbench.PacketGuiOpened;
import de.timgoll.facading.titleentities.TileBlockMachineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ContainerMachineBase extends Container {

    EntityPlayer player;
    World world;
    TileBlockMachineBase tileBlockMachineBase;

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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /// NETWORKING STUFF (just the responses, which are requested by one client)     ///
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * data transfered, when gui is opened. The response is just send to the one player opening the GUI
     */
    public void guiOpened() {
        if (!this.world.isRemote) {
            PacketHandler.INSTANCE.sendTo(
                    new PacketGuiOpened(
                            RecipeHandlerFacadingBench.productionTime.get( this.tileBlockMachineBase.getOutputBlocks_indexProducing() ),
                            this.tileBlockMachineBase.getElapsedTicksProducing(),
                            this.tileBlockMachineBase.getDisassembleTicks(),
                            this.tileBlockMachineBase.getElapsedDisassembleTicks(),
                            this.tileBlockMachineBase.getOutputBlocks_amount(),
                            this.tileBlockMachineBase.getOutputBlocks_indexProducing(),
                            this.tileBlockMachineBase.getIsPowered(),
                            this.tileBlockMachineBase.getIsProducing(),
                            this.tileBlockMachineBase.getIsDisassembling()
                    ),
                    (EntityPlayerMP) this.player
            );
        }
    }

    /**
     * The production is cancelled. Send response to all players in 6 Blocks range
     */
    public void productionCanceled() {
        this.tileBlockMachineBase.cancelProduction();

        if (!this.world.isRemote) {
            BlockPos pos = this.tileBlockMachineBase.getPos();

            PacketHandler.INSTANCE.sendToAllAround(
                    new PacketGuiCancelProduction(
                            this.tileBlockMachineBase.getOutputBlocks_amount(),
                            this.tileBlockMachineBase.getOutputBlocks_indexProducing()
                    ),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 6)
            );
        }
    }

    /**
     * a new order is sent. Send response after server verification of the order
     */
    public void productionAdded(int outputBlocks_index) {
        this.tileBlockMachineBase.addProduction(outputBlocks_index);

        if (!this.world.isRemote) {
            BlockPos pos = this.tileBlockMachineBase.getPos();

            PacketHandler.INSTANCE.sendToAllAround(
                    new PacketGuiAddProduction(
                            this.tileBlockMachineBase.getOutputBlocks_amount(),
                            this.tileBlockMachineBase.getOutputBlocks_indexProducing()
                    ),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 6)
            );
        }
    }
}
