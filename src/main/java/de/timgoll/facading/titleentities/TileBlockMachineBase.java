package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockMachineBase;
import de.timgoll.facading.misc.EnumHandler;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.facadingbench.PackedGuiFinishedProduction;
import de.timgoll.facading.network.facadingbench.PacketGuiIsPowered;
import de.timgoll.facading.network.facadingbench.PacketGuiStartedDisassembly;
import de.timgoll.facading.network.facadingbench.PacketGuiStartedProduction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileBlockMachineBase extends TileEntity implements ITickable {

    //NBT data
    ItemStackHandler inventory      = new ItemStackHandler(15);
    boolean isPowered               = false;
    boolean isProducing             = false;
    boolean isDisassembling         = false;
    int outputBlocks_indexProducing = 0;
    int outputBlocks_amount         = 0;
    int elapsedTicksProducing = 0;
    int elapsedTicksDisassembling = 0;
    //NBT data end


    int disassembleTicks            = 40; //TODO confing value

    ArrayList<Integer> outputSlots      = new ArrayList<>();
    ArrayList<Integer> inputSlots       = new ArrayList<>();
    ArrayList<Integer> disassembleSlots = new ArrayList<>();

    //cache the recipes for every type
    ArrayList<ItemStack> outputStack                = new ArrayList<>();
    ArrayList<ArrayList<ItemStack>> inputStacks     = new ArrayList<>();
    public static ArrayList<Integer> productionTime = new ArrayList<>();

    private int disassembleId = 0;

    TileBlockMachineBase() {
        outputSlots.add(11);
        outputSlots.add(12);
        outputSlots.add(13);
        outputSlots.add(14);

        inputSlots.add(0);
        inputSlots.add(1);
        inputSlots.add(2);
        inputSlots.add(3);
        inputSlots.add(4);
        inputSlots.add(5);
        inputSlots.add(6);
        inputSlots.add(7);
        inputSlots.add(8);

        disassembleSlots.add(9);
    }




    public void setIsPowered(boolean powered) {
        this.isPowered = powered;

        sendMachineIsPoweredMessage();

        BlockMachineBase.setState(this.world, this.pos);
    }

    public boolean getIsPowered() {
        return this.isPowered;
    }

    public boolean getIsWorking() {
        return isProducing || isDisassembling;
    }

    public boolean getIsDisassembling() {
        return isDisassembling;
    }

    public boolean getIsProducing() {
        return isProducing;
    }

    public Enum getType() {
        if (getIsWorking() && isPowered)
            return EnumHandler.MachineStates.WORKING;
        if (isPowered)
            return EnumHandler.MachineStates.POWERED;

        return EnumHandler.MachineStates.DEFAULT;
    }

    public int getOutputBlocks_amount() {
        return outputBlocks_amount;
    }

    public void addProduction(int outputBlocks_index) {
        if (outputBlocks_amount == 0 || outputBlocks_indexProducing == outputBlocks_index) {
            outputBlocks_amount++;
            outputBlocks_amount = (outputBlocks_amount > 99) ? 99 : outputBlocks_amount; //limit to 99
            outputBlocks_indexProducing = outputBlocks_index;
        }
    }

    public int getOutputBlocks_indexProducing() {
        return outputBlocks_indexProducing;
    }

    public int getElapsedTicksProducing() {
        return this.elapsedTicksProducing;
    }

    public int getDisassembleTicks() {
        return disassembleTicks;
    }

    public int getElapsedDisassembleTicks() {
        return elapsedTicksDisassembling;
    }




























    @Override
    public void update() {
        if (world.isRemote)
            return;

        if (!isPowered)
            return;

        if (isProducing || checkMachineState()) {
            if (elapsedTicksProducing == 0) { //new production started
                isProducing = true;
                extractNeededInput(
                        inputStacks.get(outputBlocks_indexProducing),
                        inputSlots
                );
                sendStartedProductionMessage(
                        productionTime.get(outputBlocks_indexProducing)
                );
            }

            elapsedTicksProducing++;

            if (elapsedTicksProducing >= productionTime.get(outputBlocks_indexProducing)) { //production finished

                insertOutput_single(
                        outputStack.get(outputBlocks_indexProducing),
                        outputSlots
                );

                isProducing = false;
                elapsedTicksProducing = 0;
                outputBlocks_amount--;

                sendFinishedProductionMessage();
            }
        }

        if (inventory.getStackInSlot(disassembleSlots.get(0)).getCount() > 0) {
            for (ItemStack outputs : outputStack) {
                if (inventory.getStackInSlot(disassembleSlots.get(0)).getItem().equals(outputs.getItem())) {
                    break;
                }
                disassembleId++;
            }
        }

        if ( outputStack.size() > 0 && ( inventory.getStackInSlot(disassembleSlots.get(0)).getCount() >= outputStack.get(disassembleId).getCount() ) || isDisassembling ) {
            if (elapsedTicksDisassembling == 0) {
                isDisassembling = true;

                extractNeededInput_single(
                        outputStack.get(disassembleId),
                        disassembleSlots
                );

                sendStartedDisassamblyMessage(
                        disassembleTicks
                );
            }

            elapsedTicksDisassembling++;

            if (elapsedTicksDisassembling >= disassembleTicks) {
                insertOutput(
                        inputStacks.get(disassembleId),
                        inputSlots
                );

                isDisassembling = false;
                elapsedTicksDisassembling = 0;
            }
        }
    }














    ////////////////////////////////////////////////////////////////////////////////////
    /// INVENTORY STUFF                                                              ///
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * this method checks on every update, if the machine has a production order
     * if this is the case, it checks, if the needed materials are given and there is space to output the materials
     * it also sets the machineState
     * @return the state
     */
    private boolean checkMachineState() { //TODO just set state on WorkingChange
        BlockMachineBase.setState(this.world, this.pos);
        return checkMachineState_internal();
    }

    private boolean checkMachineState_internal() {
        if (outputBlocks_amount <= 0)
            return false;

        boolean inputIsAvailable = inputIsAvailable(
                inputStacks.get(outputBlocks_indexProducing),
                inputSlots
        );

        if (!inputIsAvailable)
            return false;

        boolean outputCanBeInserted = outputCanBeInserted_single(
                outputStack.get(outputBlocks_indexProducing),
                outputSlots
        );

        if (!outputCanBeInserted)
            return false;

        return true;
    }

    /**
     * checks if the needed inputs for a recipe are available
     * @param inputStacks an ArrayList with multiple needed ItemStacks
     * @param slots an ArrayList with the slots in the ContainerInventory
     * @return the state
     */
    private boolean inputIsAvailable(ArrayList<ItemStack> inputStacks, ArrayList<Integer> slots) {
        for (ItemStack inputStack : inputStacks) {
            int amount = 0;
            int i;
            for (i = 0; i < slots.size(); i++) {
                if ( inventory.getStackInSlot(slots.get(i)).getItem().equals(inputStack.getItem()) ) {
                    amount += inventory.getStackInSlot(slots.get(i)).getCount();
                    if (amount >= inputStack.getCount())
                        break;
                }
            }
            //item not found:
            if (i == slots.size())
                return false;
        }
        return true;
    }

    /**
     * extracts the input from the slot
     * @param inputStacks an ArrayList of the Items to extract
     * @param slots an ArrayList with the slots in the ContainerInventory
     */
    private void extractNeededInput(ArrayList<ItemStack> inputStacks, ArrayList<Integer> slots) {
        for (ItemStack inputStack : inputStacks) {
            int remaining = inputStack.getCount();
            for (int slot : slots) {
                if ( inventory.getStackInSlot(slot).getItem().equals(inputStack.getItem()) ) {
                    int amountInSlot = inventory.getStackInSlot(slot).getCount();
                    inventory.extractItem(slot, remaining, false);
                    remaining -= amountInSlot;

                    if (remaining <= 0)
                        break;
                }
            }
        }
    }

    private void extractNeededInput_single(ItemStack inputStacks, ArrayList<Integer> slots) {
        ArrayList<ItemStack> outputStacks = new ArrayList<>();
        outputStacks.add(inputStacks);

        extractNeededInput(outputStacks, slots);
    }

    /**
     * Checks if an slot is empty or stackable
     * @param outputStacks an ArrayList of the Items to add
     * @param slots an ArrayList with the slots in the ContainerInventory
     * @return the state
     */
    private boolean outputCanBeInserted(ArrayList<ItemStack> outputStacks, ArrayList<Integer> slots) {
        for (ItemStack outputStack : outputStacks) {
            int freeslots = 0;
            int i;
            for (i = 0; i < slots.size(); i++) {
                if (inventory.getStackInSlot(slots.get(i)).isEmpty())
                    break;

                if (inventory.getStackInSlot(slots.get(i)).getItem().equals(outputStack.getItem()))
                    freeslots += outputStack.getMaxStackSize() - inventory.getStackInSlot(slots.get(i)).getCount();

                if (freeslots >= outputStack.getCount())
                    break;

            }
            //item not found:
            if (i == slots.size())
                return false;

        }

        return true;
    }

    private boolean outputCanBeInserted_single(ItemStack outputStack, ArrayList<Integer> slots) {
        ArrayList<ItemStack> outputStacks = new ArrayList<>();
        outputStacks.add(outputStack);

        return outputCanBeInserted(outputStacks, slots);
    }

    /**
     * inserts items in the first empty/stackable slots
     * @param outputStacks an ArrayList of the Items to add
     * @param slots an ArrayList with the slots in the ContainerInventory
     */
    private void insertOutput(ArrayList<ItemStack> outputStacks, ArrayList<Integer> slots) {
        for (ItemStack _outputStack : outputStacks) {
            ItemStack outputStack = _outputStack.copy();
            for (int slot : slots) {
                if (!inventory.getStackInSlot(slot).getItem().equals(outputStack.getItem()) && !inventory.getStackInSlot(slot).isEmpty())
                    continue;

                int freespace = outputStack.getMaxStackSize() - inventory.getStackInSlot(slot).getCount();

                if (freespace < outputStack.getCount()) { //needs to be splitted
                    ItemStack splitted = outputStack.splitStack(outputStack.getCount() - freespace);
                    inventory.insertItem(slot, outputStack, false);
                    outputStack = splitted;
                } else {
                    inventory.insertItem(slot, outputStack, false);
                    break;
                }
            }
        }
    }

    private void insertOutput_single(ItemStack outputStack, ArrayList<Integer> slots) {
        ArrayList<ItemStack> outputStacks = new ArrayList<>();
        outputStacks.add(outputStack);

        insertOutput(outputStacks, slots);
    }


    ////////////////////////////////////////////////////////////////////////////////////
    /// NETWORKING STUFF                                                             ///
    ////////////////////////////////////////////////////////////////////////////////////

    public void cancelProduction() { //TODO: call on Blockbreak
        if (isProducing) {
            insertOutput(
                    inputStacks.get(outputBlocks_indexProducing),
                    inputSlots
            );
        }
        isProducing           = false;
        outputBlocks_amount   = 0;
        elapsedTicksProducing = 0;
    }


    private void sendMachineIsPoweredMessage() {
        if (!this.world.isRemote) {
            BlockPos pos = this.getPos();

            PacketHandler.INSTANCE.sendToAllAround(
                    new PacketGuiIsPowered(
                            isPowered
                    ),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 6)
            );
        }
    }

    private void sendStartedProductionMessage(int productionTicks) {
        if (!this.world.isRemote) {
            BlockPos pos = this.getPos();

            PacketHandler.INSTANCE.sendToAllAround(
                    new PacketGuiStartedProduction(
                            productionTicks
                    ),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 6)
            );
        }
    }

    private void sendStartedDisassamblyMessage(int disassemblyTicks) {
        if (!this.world.isRemote) {

            BlockPos pos = this.getPos();

            PacketHandler.INSTANCE.sendToAllAround(
                    new PacketGuiStartedDisassembly(
                            disassemblyTicks
                    ),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 6)
            );
        }
    }

    private void sendFinishedProductionMessage() {
        if (!this.world.isRemote) {
            BlockPos pos = this.getPos();

            PacketHandler.INSTANCE.sendToAllAround(
                    new PackedGuiFinishedProduction(
                            outputBlocks_amount
                    ),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 6)
            );
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////
    /// SYNCING AND NBT STUFF                                                        ///
    ////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        inventory.deserializeNBT(compound.getCompoundTag("inventory"));

        this.isPowered                   = compound.getBoolean("isPowered");
        this.isProducing                 = compound.getBoolean("isProducing");
        this.isDisassembling             = compound.getBoolean("isDisassembling");
        this.outputBlocks_indexProducing = compound.getInteger("outputBlocks_indexProducing");
        this.outputBlocks_amount         = compound.getInteger("outputBlocks_amount");
        this.elapsedTicksProducing       = compound.getInteger("elapsedTicksProducing");
        this.elapsedTicksDisassembling   = compound.getInteger("elapsedTicksDisassembling");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());

        compound.setBoolean("isPowered", isPowered);
        compound.setBoolean("isProducing", isProducing);
        compound.setBoolean("isDisassembling", isDisassembling);
        compound.setInteger("outputBlocks_indexProducing", outputBlocks_indexProducing);
        compound.setInteger("outputBlocks_amount", outputBlocks_amount);
        compound.setInteger("elapsedTicksProducing", elapsedTicksProducing);
        compound.setInteger("elapsedTicksDisassembling", elapsedTicksDisassembling);

        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), -999, writeToNBT(new NBTTagCompound()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }

}
