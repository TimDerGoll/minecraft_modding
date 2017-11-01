package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockMachineBase;
import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import net.minecraft.nbt.NBTTagCompound;

public class TileBlockFacadingbench extends TileBlockMachineBase {

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

        if (!waterPowerActivated)
            return;

        if (outputBlocks_amount > 0) {
            if (!machineIsWorking) {
                machineIsWorking = true;
                BlockMachineBase.setState(this.world, this.pos);
            }

            elapsedTicks++;
            if (elapsedTicks >= RecipeHandlerFacadingBench.productionTime.get(outputBlocks_index_producing)) {
                inventory.insertItem(
                        11,
                        RecipeHandlerFacadingBench.outputStack.get(outputBlocks_index_producing).copy(),
                        false
                );
                elapsedTicks = 0;
                outputBlocks_amount--;
            }
        } else {
            if (machineIsWorking) {
                machineIsWorking = false;
                BlockMachineBase.setState(this.world, this.pos);
            }
        }


    }

}
