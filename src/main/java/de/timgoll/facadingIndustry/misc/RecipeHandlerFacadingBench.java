package de.timgoll.facadingIndustry.misc;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class RecipeHandlerFacadingBench {

    public static ArrayList<ItemStack> outputStack = new ArrayList<>();
    public static ArrayList<ArrayList<ItemStack>> inputStacks = new ArrayList<>();
    public static ArrayList<Integer> productionTime = new ArrayList<>();

    public static void addRecipe(ItemStack output, ArrayList<ItemStack> inputs, int time) {
        outputStack.add(output);
        inputStacks.add(inputs);
        productionTime.add(time);
    }

    public static int size() {
        return outputStack.size();
    }

}
