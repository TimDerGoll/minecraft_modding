package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockFacadingbench;
import de.timgoll.facading.init.ModRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileBlockFacadingbench extends TileBlockMachineBase {

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        System.out.println("reading inventory OVERLAY ..............................");


        super.readFromNBT(compound);
        //more stuff here

        System.out.println("reading inventory 6 ..............................");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        //more stuff here
        return super.writeToNBT(compound);
    }

    @Override
    public void setWaterPowerActivated(boolean waterPowerActivated) {
        super.setWaterPowerActivated(waterPowerActivated);

        BlockFacadingbench.setState(this.waterPowerActivated, this.machineIsWorking, this.world, this.pos);
    }

    @Override
    public void update() {
        super.update();
        //System.out.println("main loop of entity");
    }

}
