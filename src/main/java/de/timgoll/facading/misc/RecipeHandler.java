package de.timgoll.facading.misc;

import de.timgoll.facading.init.ModRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler {

    public void registerRecipes() {
        addHammer();
        addFacadingbench();
    }

    /* ** RECIPES ** */
    private void addHammer() {
        GameRegistry.addShapedRecipe(
                new ResourceLocation("hammer_iron"),
                new ResourceLocation("facading"),
                new ItemStack(ModRegistry.ITEM_HAMMER),
                "III",
                " SI",
                "W  ",
                'I', "ingotIron",
                'S', "stickWood",
                'W', "plankWood"
        );
        GameRegistry.addShapedRecipe(
                new ResourceLocation("hammer_copper"),
                new ResourceLocation("facading"),
                new ItemStack(ModRegistry.ITEM_HAMMER),
                "III",
                " SI",
                "W  ",
                'I', "ingotCopper",
                'S', "stickWood",
                'W', "plankWood"
        );
    }

    private void addFacadingbench() {
        GameRegistry.addShapedRecipe(
                new ResourceLocation("facadingbench"),
                new ResourceLocation("facading"),
                new ItemStack(ModRegistry.BLOCK_FACADINGBENCH),
                " C ",
                "WHW",
                "SSS",
                'C', Blocks.CRAFTING_TABLE,
                'H', ModRegistry.ITEM_HAMMER,
                'W', "plankWood",
                'S', "stone"
        );
    }

}
