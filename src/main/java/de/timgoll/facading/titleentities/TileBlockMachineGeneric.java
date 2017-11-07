package de.timgoll.facading.titleentities;

import net.minecraft.item.ItemStack;

public class TileBlockMachineGeneric extends TileBlockMachineBase {

    public TileBlockMachineGeneric() {
        //configuring TE
        hasProduction  = true;
        hasDisassembly = false;

        //Setting up slots
        inputSlots.add(0);

        outputSlots.add(1);
        outputSlots.add(2);
        outputSlots.add(3);
        outputSlots.add(4);
    }

    @Override
    public void update() {
        getOutputId();

        super.update();
    }

    private void getOutputId() {
        if (!isPowered)
            return;

        if (isProducing)
            return;

        //search for input and set output_index
        ItemStack input = inventory.getStackInSlot(inputSlots.get(0));
        if (input.getCount() == 0)
            return;

        //get the ID of the inserted Block
        int outputBlocks_indexProducing_tmp = 0;
        for (ItemStack outputs : outputStack) {
            if (input.getItem().equals( outputs.getItem() )) {
                break;
            }
            outputBlocks_indexProducing_tmp++;
        }
        outputBlocks_indexProducing = outputBlocks_indexProducing_tmp;

    }
}
