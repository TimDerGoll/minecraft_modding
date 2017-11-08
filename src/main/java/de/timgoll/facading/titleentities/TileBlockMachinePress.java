package de.timgoll.facading.titleentities;

import de.timgoll.facading.misc.CustomRecipeRegistry;
import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import de.timgoll.facading.misc.RecipeHandlerPress;
import net.minecraftforge.items.ItemStackHandler;

public class TileBlockMachinePress extends TileBlockMachineGeneric {

    public TileBlockMachinePress() {
        super("press");
        inventory = new ItemStackHandler(6);
    }

}
