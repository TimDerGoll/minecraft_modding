package de.timgoll.facadingIndustry.titleentities;

import de.timgoll.facadingIndustry.misc.RecipeHandlerFacadingBench;
import net.minecraftforge.items.ItemStackHandler;

public class TileBlockPress extends TileBlockMachineBase {

    public TileBlockPress() {
        inventory = new ItemStackHandler(5);

        outputStack    = RecipeHandlerFacadingBench.outputStack;
        inputStacks    = RecipeHandlerFacadingBench.inputStacks;
        productionTime = RecipeHandlerFacadingBench.productionTime;

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

}
