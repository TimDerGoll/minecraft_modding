package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockFacadingbench;
import de.timgoll.facading.blocks.BlockMachineBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileBlockMachineBase extends TileEntity implements ITickable {

    protected ItemStackHandler inventory = new ItemStackHandler(15);
    protected boolean waterPowerActivated = false;
    protected boolean machineIsWorking = false;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.waterPowerActivated = compound.getBoolean("waterPowerActivated");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setBoolean("waterPowerActivated", waterPowerActivated);
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
    }

    public boolean getWaterPowerActivated() {
        return this.waterPowerActivated;
    }

    @Override
    public void update() {

    }

}
