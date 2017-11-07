package de.timgoll.facadingIndustry.titleentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

//https://gitlab.com/p455w0rd/p455w0rdsThings/blob/master/src/main/java/p455w0rd/p455w0rdsthings/blocks/tileentities/TileMachineBase.java#L254-268

public class TileBlockFacade extends TileEntity {
    private final String TAG_STATE = "state";


    public boolean isSlab = false;

    public TileBlockFacade() {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList nbtList = new NBTTagList();

        //nbtList.setBoolean("isSlab", isSlab);

        compound.setBoolean("isSlab", isSlab);
        super.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        isSlab = compound.getBoolean("isSlab");
    }



    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 255, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

}
