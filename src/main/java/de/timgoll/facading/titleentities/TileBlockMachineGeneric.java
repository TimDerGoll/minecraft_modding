package de.timgoll.facading.titleentities;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TileBlockMachineGeneric extends TileBlockMachineBase {

    public TileBlockMachineGeneric(String machinetype) {
        super(machinetype);

        //configuring TE
        hasProduction  = true;
        hasDisassembly = false;

        //Setting up slots
        inputSlots.add(0);

        upgradeSlot.add(1);

        outputSlots.add(2);
        outputSlots.add(3);
        outputSlots.add(4);
        outputSlots.add(5);
    }

    @Override
    public void update() {
        getOutputId();

        super.update();
    }

    private void getOutputId() {
        if (world.isRemote)
            return;

        if (!isPowered)
            return;

        if (isProducing)
            return;

        //search for input and set output_index
        ItemStack input = inventory.getStackInSlot(inputSlots.get(0));
        if (input.isEmpty())
            return;

        //get the ID of the inserted Block
        int outputBlocks_indexProducing_tmp = 0;
        for (ArrayList<ArrayList<ItemStack>> inputs : inputStacks) {
            //generic machines have just one input
            ArrayList<ItemStack> inputRecipe = inputs.get(0);

            if ( isInStackList(inputRecipe, input.getItem()) ) {
                outputBlocks_indexProducing = outputBlocks_indexProducing_tmp;
                outputBlocks_amount = input.getCount();
                break;
            }
            outputBlocks_indexProducing_tmp++;
        }

    }
}
