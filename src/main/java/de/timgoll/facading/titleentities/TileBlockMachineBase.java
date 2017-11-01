package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockMachineBase;
import de.timgoll.facading.misc.EnumHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileBlockMachineBase extends TileEntity implements ITickable {

    protected ItemStackHandler inventory       = new ItemStackHandler(15);
    protected boolean waterPowerActivated      = false;
    protected boolean machineIsWorking         = false;
    protected int outputBlocks_index_producing = 0;
    protected int outputBlocks_amount          = 0;
    protected int elapsedTicks                 = 0;

    private boolean loaded = false;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        inventory.deserializeNBT(compound.getCompoundTag("inventory"));

        this.waterPowerActivated          = compound.getBoolean("waterPowerActivated");
        this.machineIsWorking             = compound.getBoolean("machineIsWorking");
        this.outputBlocks_index_producing = compound.getInteger("outputBlocks_index_producing");
        this.outputBlocks_amount          = compound.getInteger("outputBlocks_amount");
        this.elapsedTicks                 = compound.getInteger("elapsedTicks");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());

        compound.setBoolean("waterPowerActivated", waterPowerActivated);
        compound.setBoolean("machineIsWorking", machineIsWorking);
        compound.setInteger("outputBlocks_index_producing", outputBlocks_index_producing);
        compound.setInteger("outputBlocks_amount", outputBlocks_amount);
        compound.setInteger("elapsedTicks", elapsedTicks);

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


    public void setWaterPowerActivated(boolean waterPowerActivated) {
        this.waterPowerActivated = waterPowerActivated;

        BlockMachineBase.setState(this.world, this.pos);
    }

    public boolean getWaterPowerActivated() {
        return this.waterPowerActivated;
    }

    public Enum getType() {
        if (machineIsWorking)
            return EnumHandler.MachineStates.WORKING;
        if (waterPowerActivated)
            return EnumHandler.MachineStates.POWERED;

        return EnumHandler.MachineStates.DEFAULT;
    }

    public void cancelProduction() {
        outputBlocks_amount = 0;
    }

    public int getOutputBlocks_amount() {
        return outputBlocks_amount;
    }

    public void addProduction(int outputBlocks_index) {
        if (outputBlocks_amount == 0 || outputBlocks_index_producing == outputBlocks_index) {
            outputBlocks_amount++;
            outputBlocks_amount = (outputBlocks_amount > 99) ? 99 : outputBlocks_amount; //limit to 99
            outputBlocks_index_producing = outputBlocks_index;
        }
    }

    public int getOutputBlocks_index_producing() {
        return outputBlocks_index_producing;
    }




    //SYNCING STUFF
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






    @Override
    public void update() {

    }

}
