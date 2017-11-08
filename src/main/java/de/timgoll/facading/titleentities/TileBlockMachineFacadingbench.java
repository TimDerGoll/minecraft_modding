package de.timgoll.facading.titleentities;

import de.timgoll.facading.misc.CustomRecipeRegistry;
import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import net.minecraftforge.items.ItemStackHandler;

public class TileBlockMachineFacadingbench extends TileBlockMachineBase {

    public TileBlockMachineFacadingbench() {
        super("facadingbench");

        inventory = new ItemStackHandler(15);

        recipeList = CustomRecipeRegistry.getRecipeList("facadingbench");

        //outputStack    = RecipeHandlerFacadingBench.outputStack;
        //inputStacks    = RecipeHandlerFacadingBench.inputStacks;
        //productionTime = RecipeHandlerFacadingBench.productionTime;

        //configuring TE
        hasProduction  = true;
        hasDisassembly = true;

        //Setting up slots
        inputSlots.add(0);
        inputSlots.add(1);
        inputSlots.add(2);
        inputSlots.add(3);
        inputSlots.add(4);
        inputSlots.add(5);
        inputSlots.add(6);
        inputSlots.add(7);
        inputSlots.add(8);

        disassembleSlots.add(9);

        upgradeSlot.add(10);

        outputSlots.add(11);
        outputSlots.add(12);
        outputSlots.add(13);
        outputSlots.add(14);
    }

}
