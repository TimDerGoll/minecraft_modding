package de.timgoll.facading.titleentities;

import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import net.minecraftforge.items.ItemStackHandler;

public class TileBlockMachineSawmill extends TileBlockMachineGeneric {

    public TileBlockMachineSawmill() {
        super();

        inventory = new ItemStackHandler(6);

        outputStack    = RecipeHandlerFacadingBench.outputStack;
        inputStacks    = RecipeHandlerFacadingBench.inputStacks;
        productionTime = RecipeHandlerFacadingBench.productionTime;
    }

}