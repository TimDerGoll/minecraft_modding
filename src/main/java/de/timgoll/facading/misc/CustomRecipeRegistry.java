package de.timgoll.facading.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomRecipeRegistry {

    private static Map<String, ArrayList<MachineRecipe>> recipes = new HashMap<>();

    public static void addMachineRecipe(String machinetype, @Nonnull ItemStack output, int productiontime, Object... params) {
        ArrayList<ArrayList<ItemStack>> inputList = new ArrayList<>();

        for (int i = 0; i < params.length; i++) {
            //values should either be String or Item
            if (params[i] instanceof String) { //orecictionary entry
                NonNullList<ItemStack> oreList = OreDictionary.getOres((String)params[i]);
                ArrayList<ItemStack> oreInputs = new ArrayList<>();
                for (ItemStack input : oreList)
                    oreInputs.add(input);

                inputList.add(oreInputs);
            } else
            if (params[i] instanceof ItemStack) { //normal item
                ArrayList<ItemStack> itemAsList = new ArrayList<>();
                itemAsList.add((ItemStack) params[i]);

                inputList.add(itemAsList);

            } else
            if (params[i] instanceof ArrayList) {
                inputList.add((ArrayList) params[i]);
            }
        }
        System.out.println(inputList);

        if (inputList.size() == 0 || inputList.get(0).size() == 0)
            return;

        System.out.println("added");

        addToArrayList(
                recipes,
                machinetype,
                new MachineRecipe(
                        output,
                        inputList,
                        productiontime
                )
        );
    }

    public static ArrayList<MachineRecipe> getRecipeList(String machinetype) {
        return recipes.get(machinetype);
    }

    public static ArrayList<ItemStack> getOutputList(String machinetype) {
        ArrayList<ItemStack> outputList = new ArrayList<>();

        for (MachineRecipe recipe : getRecipeList(machinetype))
            outputList.add(recipe.getOutputStack());

        return outputList;
    }

    public static ArrayList<ArrayList<ArrayList<ItemStack>>> getInputList (String machinetype) {
        ArrayList<ArrayList<ArrayList<ItemStack>>> inputList = new ArrayList<>();

        for (MachineRecipe recipe : getRecipeList(machinetype))
            inputList.add(recipe.getInputStackList());

        return inputList;
    }

    public static ArrayList<Integer> getProductionTimeList(String machinetype) {
        ArrayList<Integer> productionTimeList = new ArrayList<>();

        for (MachineRecipe recipe : getRecipeList(machinetype))
            productionTimeList.add(recipe.getProductiontime());

        return productionTimeList;
    }

    public static class MachineRecipe {
        private int productiontime;
        private ItemStack outputStack;
        private ArrayList<ArrayList<ItemStack>> inputStackList; //inner Array represents different alternatives for this one item

        MachineRecipe(ItemStack outputStack, ArrayList<ArrayList<ItemStack>> inputStackList, int productiontime) {
            this.outputStack    = outputStack;
            this.inputStackList = inputStackList;
            this.productiontime = productiontime;
        }

        public int getProductiontime() {
            return this.productiontime;
        }

        public ItemStack getOutputStack() {
            return this.outputStack;
        }

        public ArrayList<ArrayList<ItemStack>> getInputStackList() {
            return this.inputStackList;
        }
    }




    private static void addToArrayList(Map<String, ArrayList<MachineRecipe>> hashmap, String mapKey, MachineRecipe myItem) {
        ArrayList<MachineRecipe> itemsList = hashmap.get(mapKey);

        // if list does not exist create it
        if(itemsList == null) {
            itemsList = new ArrayList<>();
            itemsList.add(myItem);
            hashmap.put(mapKey, itemsList);
        } else {
            // add if item is not already in list
            if(!itemsList.contains(myItem)) itemsList.add(myItem);
        }
    }

}
