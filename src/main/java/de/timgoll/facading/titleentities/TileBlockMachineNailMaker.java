package de.timgoll.facading.titleentities;

import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import net.minecraftforge.items.ItemStackHandler;

public class TileBlockMachineNailMaker extends TileBlockMachineGeneric {

    public TileBlockMachineNailMaker() {
        super("press");

        inventory = new ItemStackHandler(6);

        //outputStack    = RecipeHandlerFacadingBench.outputStack;
        //inputStacks    = RecipeHandlerFacadingBench.inputStacks;
        //productionTime = RecipeHandlerFacadingBench.productionTime;
    }

}