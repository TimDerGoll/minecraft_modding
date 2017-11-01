package de.timgoll.facading.titleentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileBlockPress extends TileBlockMachineBase {

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        //more stuff here
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        //more stuff here
        return super.writeToNBT(compound);
    }


    @Override
    public void update() {
        super.update();
        //System.out.println("main loop of entity");
    }

}
