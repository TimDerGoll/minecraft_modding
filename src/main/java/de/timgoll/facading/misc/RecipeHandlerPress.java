package de.timgoll.facading.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class RecipeHandlerPress {

    public static ArrayList<ItemStack> outputStack = new ArrayList<>();
    public static ArrayList<ArrayList<ItemStack>> inputStacks = new ArrayList<>();
    public static ArrayList<Integer> productionTime = new ArrayList<>();

    static void addRecipe(ItemStack output, ArrayList<ItemStack> inputs, int time) {
        outputStack.add(output);
        inputStacks.add(inputs);
        productionTime.add(time);
    }

    static void addOreRecipe(ItemStack output, String orename, int time) {
        NonNullList<ItemStack> oreList = OreDictionary.getOres(orename);
        ArrayList<ItemStack> oreInputs = new ArrayList<>();

        for (ItemStack input : oreList) {
            oreInputs.add(input);
        }

        outputStack.add(output);
        inputStacks.add(oreInputs);
        productionTime.add(time);
    }

    public static int size() {
        return outputStack.size();
    }

}
