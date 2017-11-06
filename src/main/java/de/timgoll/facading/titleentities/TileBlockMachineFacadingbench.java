package de.timgoll.facading.titleentities;

import de.timgoll.facading.misc.RecipeHandlerFacadingBench;

public class TileBlockMachineFacadingbench extends TileBlockMachineBase {

    public TileBlockMachineFacadingbench() {
        outputStack    = RecipeHandlerFacadingBench.outputStack;
        inputStacks    = RecipeHandlerFacadingBench.inputStacks;
        productionTime = RecipeHandlerFacadingBench.productionTime;
    }

}
