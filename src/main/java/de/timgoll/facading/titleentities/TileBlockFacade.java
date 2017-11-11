package de.timgoll.facading.titleentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class TileBlockFacade extends TileEntity {

    private byte blockState = 0b00000000; //only 6 bits used // 0x 0 0 north south west east up down

    public TileBlockFacade() {

    }

    public void transform(EnumFacing facing) {
        switch (facing) {
            case NORTH:
                transformNorth(); break;
            case SOUTH:
                transformSouth(); break;
            case WEST:
                transformWest(); break;
            case EAST:
                transformEast(); break;
            case UP:
                transformUp(); break;
            case DOWN:
                transformDown(); break;
            default:
                transformDefault(); break;
        }
        //set state to render
    }

    private void transformNorth() {
        byte setNorth = 0b00100000;
        byte setSouth = 0b00101111;

        blockState = (byte) (blockState & ~(1 << setNorth));
        blockState = (byte) (blockState |  (1 << setSouth));
    }

    private void transformSouth() {
        byte setNorth = 0b00011111;
        byte setSouth = 0b00010000;

        blockState = (byte) (blockState |  (1 << setNorth));
        blockState = (byte) (blockState & ~(1 << setSouth));
    }

    private void transformWest() {
        byte setWest = 0b00001000;
        byte setEast = 0b00111011;

        blockState = (byte) (blockState & ~(1 << setWest));
        blockState = (byte) (blockState |  (1 << setEast));
    }

    private void transformEast() {
        byte setWest = 0b00000100;
        byte setEast = 0b00110111;

        blockState = (byte) (blockState |  (1 << setWest));
        blockState = (byte) (blockState & ~(1 << setEast));
    }

    private void transformUp() {
        byte setUp   = 0b00000010;
        byte setDown = 0b00111110;

        blockState = (byte) (blockState & ~(1 << setUp));
        blockState = (byte) (blockState |  (1 << setDown));
    }

    private void transformDown() {
        byte setUp   = 0b00000001;
        byte setDown = 0b00111101;

        blockState = (byte) (blockState |  (1 << setUp));
        blockState = (byte) (blockState & ~(1 << setDown));
    }

    private void transformDefault() {
        blockState = 0b00000000;
    }



    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList nbtList = new NBTTagList();

        //nbtList.setBoolean("isSlab", isSlab);

        compound.setByte("blockState", blockState);
        super.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        blockState = compound.getByte("blockState");
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
