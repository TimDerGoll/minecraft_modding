package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockFacadingbench;
import de.timgoll.facading.blocks.BlockMachineBase;
import de.timgoll.facading.misc.EnumHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileBlockMachineBase extends TileEntity implements ITickable {

    protected ItemStackHandler inventory  = new ItemStackHandler(15);
    protected boolean waterPowerActivated = false;
    protected boolean machineIsWorking    = false;

    private boolean loaded = false;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.waterPowerActivated = compound.getBoolean("waterPowerActivated");
        this.machineIsWorking = compound.getBoolean("machineIsWorking");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setBoolean("waterPowerActivated", waterPowerActivated);
        compound.setBoolean("machineIsWorking", machineIsWorking);
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

        BlockMachineBase.setState(this.world, this.pos, this.getType());
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
