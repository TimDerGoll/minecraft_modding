package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockFacade;
import de.timgoll.facading.misc.Common;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.BitSet;

public class TileBlockFacade extends TileEntity {

    private ItemStackHandler inventory = new ItemStackHandler(3);
    private boolean changedNorth;
    private boolean changedSouth;
    private boolean changedWest;
    private boolean changedEast;
    private boolean changedUp;
    private boolean changedDown;

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
        BlockFacade.setState(this.world, this.pos);
    }

    public void transform_reversed(EnumFacing facing) {
        switch (facing) {
            case NORTH:
                transformNorth_reversed(); break;
            case SOUTH:
                transformSouth_reversed(); break;
            case WEST:
                transformWest_reversed(); break;
            case EAST:
                transformEast_reversed(); break;
            case UP:
                transformUp_reversed(); break;
            case DOWN:
                transformDown_reversed(); break;
            default:
                transformDefault(); break;
        }
        BlockFacade.setState(this.world, this.pos);
    }

    public boolean north() {
        return changedNorth;
    }

    public boolean south() {
        return changedSouth;
    }

    public boolean west() {
        return changedWest;
    }

    public boolean east() {
        return changedEast;
    }

    public boolean up() {
        return changedUp;
    }

    public boolean down() {
        return changedDown;
    }

    private void transformNorth_reversed() {
        changedNorth = false;
    }

    private void transformSouth_reversed() {
        changedSouth = false;
    }

    private void transformWest_reversed() {
        changedWest = false;
    }

    private void transformEast_reversed() {
        changedEast = false;
    }

    private void transformUp_reversed() {
        changedUp   = false;
    }

    private void transformDown_reversed() {
        changedDown = false ;
    }

    private void transformNorth() {
        changedNorth = true;
        changedSouth = false;
    }

    private void transformSouth() {
        changedNorth = false;
        changedSouth = true;
    }

    private void transformWest() {
        changedWest = true;
        changedEast = false;
    }

    private void transformEast() {
        changedWest = false;
        changedEast = true;
    }

    private void transformUp() {
        changedUp   = true;
        changedDown = false;
    }

    private void transformDown() {
        changedUp   = false;
        changedDown = true;
    }

    private void transformDefault() {
        changedNorth = false;
        changedSouth = false;
        changedWest  = false;
        changedEast  = false;
        changedUp    = false;
        changedDown  = false;
    }


    ////////////////////////////////////////////////////////////////////////////////////
    /// SYNCING AND NBT STUFF                                                        ///
    ////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        inventory.deserializeNBT(compound.getCompoundTag("inventory"));

        changedNorth = compound.getBoolean("changedNorth");
        changedSouth = compound.getBoolean("changedSouth");
        changedWest  = compound.getBoolean("changedWest");
        changedEast  = compound.getBoolean("changedEast");
        changedUp    = compound.getBoolean("changedUp");
        changedDown  = compound.getBoolean("changedDown");

        BlockFacade.setState(this.world, this.pos);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());

        compound.setBoolean("changedNorth", changedNorth);
        compound.setBoolean("changedSouth", changedSouth);
        compound.setBoolean("changedWest", changedWest);
        compound.setBoolean("changedNorth", changedEast);
        compound.setBoolean("changedUp", changedUp);
        compound.setBoolean("changedDown", changedDown);

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