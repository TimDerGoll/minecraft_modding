package de.timgoll.facading.titleentities;

import de.timgoll.facading.blocks.BlockMachineBase;
import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.facadingbench.PackedGuiFinishedProduction;
import de.timgoll.facading.network.facadingbench.PacketGuiStartedProduction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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

            if (elapsedTicks == 0) { //new production started
                startedProductionMessage(
                        RecipeHandlerFacadingBench.productionTime.get(outputBlocks_index_producing)
                );
            }

            elapsedTicks++;

            if (elapsedTicks >= RecipeHandlerFacadingBench.productionTime.get(outputBlocks_index_producing)) { //production finished
                inventory.insertItem(
                        11,
                        RecipeHandlerFacadingBench.outputStack.get(outputBlocks_index_producing).copy(),
                        false
                );
                elapsedTicks = 0;
                outputBlocks_amount--;

                finishedProductionMessage();
            }
        } else {
            if (machineIsWorking) {
                machineIsWorking = false;
                BlockMachineBase.setState(this.world, this.pos);
            }
        }


    }



    private void startedProductionMessage(int productionTicks) {
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

    private void finishedProductionMessage() {
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

}
