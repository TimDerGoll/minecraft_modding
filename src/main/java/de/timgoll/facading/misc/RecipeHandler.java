package de.timgoll.facading.misc;

import de.timgoll.facading.init.ModRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.ArrayList;

public class RecipeHandler {

    public void registerRecipes() {
        addHammer();
        addToolHolder();
        addWaterMill();

        addIronToothSaw();
        addDiamondCicularSaw();
        addNetherSaw();

        addMachineframe();

        addUncompressedDemerald();

        addPress();
        addFacadingbench();



        addFacade();
        addPlaceholder();
        addPlaceholder2();

        addReinforcementBundle();
        addEmeraldPlate();
        addDiamondPlate();
        addDemerald();

        OreDictionary.getOres("iron");
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

    private void addToolHolder() {
        GameRegistry.addShapedRecipe(
            new ResourceLocation("toolholder"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_TOOLHOLDER),
            " WW",
            "SWW",
            "S  ",
            'W', "plankWood",
            'S', "stickWood"
        );
    }

    private void addWaterMill() {
        GameRegistry.addShapedRecipe(
            new ResourceLocation("watermill_iron"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_WATERMILL),
            " W ",
            "WIW",
            " W ",
            'W', "plankWood",
            'I', "ingotIron"
        );

        GameRegistry.addShapedRecipe(
            new ResourceLocation("watermill_copper"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_WATERMILL),
            " W ",
            "WIW",
            " W ",
            'W', "plankWood",
            'I', "ingotCopper"
        );
    }

    private void addIronToothSaw() {
        GameRegistry.addShapedRecipe(
            new ResourceLocation("irontoothsaw_iron"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_IRONTOOTHSAW),
            "  S",
            "IIS",
            "III",
            'I', "ingotIron",
            'S', "stickWood"
        );

        GameRegistry.addShapedRecipe(
            new ResourceLocation("irontoothsaw_copper"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_IRONTOOTHSAW),
            "  S",
            "IIS",
            "III",
            'I', "ingotCopper",
            'S', "stickWood"
        );
    }

    private void addDiamondCicularSaw() {
        GameRegistry.addShapedRecipe(
            new ResourceLocation("diamondcicularsaw_iron"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_DIAMONDCIRCULARSAW),
            " D ",
            "DID",
            " D ",
            'I', "ingotIron",
            'D', "gemDiamond"
        );

        GameRegistry.addShapedRecipe(
            new ResourceLocation("diamondcicularsaw_copper"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.ITEM_DIAMONDCIRCULARSAW),
            " D ",
            "DID",
            " D ",
            'I', "ingotCopper",
            'D', "gemDiamond"
        );
    }

    private void addNetherSaw() {
        GameRegistry.addShapedRecipe(
                new ResourceLocation("nethersaw"),
                new ResourceLocation("facading"),
                new ItemStack(ModRegistry.ITEM_NETHERSAW),
                "SSS",
                "II ",
                "   ",
                'I', "blockCopper",
                'S', "stickWood"
        );
    }






    private void addMachineframe() {
        GameRegistry.addShapedRecipe(
            new ResourceLocation("machineframe"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.BLOCK_MACHINEFRAME),
            "RRR",
            "R R",
            "RRR",
            'R', ModRegistry.ITEM_REINFORCEMENTBUNDLE
        );
    }

    private void addPress() {
        GameRegistry.addShapedRecipe(
                new ResourceLocation("press"),
                new ResourceLocation("facading"),
                new ItemStack(ModRegistry.BLOCK_PRESS),
                "P P",
                "#RT",
                "SSS",
                'P', Blocks.PISTON,
                '#', ModRegistry.ITEM_WATERMILL,
                'T', ModRegistry.ITEM_TOOLHOLDER,
                'R', "dustRedstone",
                'S', "stone"
        );
    }

    private void addFacadingbench() {
        GameRegistry.addShapedRecipe(
            new ResourceLocation("facadingbench"),
            new ResourceLocation("facading"),
            new ItemStack(ModRegistry.BLOCK_FACADINGBENCH),
            "WCW",
            "#MT",
            "SSS",
            'C', Blocks.CRAFTING_TABLE,
            'M', ModRegistry.BLOCK_MACHINEFRAME,
            '#', ModRegistry.ITEM_WATERMILL,
            'T', ModRegistry.ITEM_TOOLHOLDER,
            'W', "plankWood",
            'S', "stone"
        );
    }

    private void addUncompressedDemerald() {
        GameRegistry.addShapedRecipe(
                new ResourceLocation("uncompressed_demerald"),
                new ResourceLocation("facading"),
                new ItemStack(ModRegistry.ITEM_UNCOMPRESSED_DEMERALD),
                " E ",
                " S ",
                " D ",
                'E', ModRegistry.ITEM_EMERALD_PLATE,
                'S', "slimeball",
                'D', ModRegistry.ITEM_DIAMOND_PLATE
        );
    }










    // CUSTOM CRAFTING METHODS
    private void addFacade() {
        CustomRecipeRegistry.addMachineRecipe(
                "facadingbench",
                new ItemStack(ModRegistry.BLOCK_FACADE, 8),
                2000,
                new ItemStack(ModRegistry.ITEM_BOXOFNAILS, 1),
                new ItemStack(ModRegistry.ITEM_FRAMEBUNDLE, 8)
        );
    }

    private void addPlaceholder() {
        CustomRecipeRegistry.addMachineRecipe(
                "facadingbench",
                new ItemStack(Blocks.BOOKSHELF, 16),
                10000,
                new ItemStack(ModRegistry.ITEM_BOXOFNAILS, 1),
                new ItemStack(ModRegistry.ITEM_FRAMEBUNDLE, 8)
        );
    }

    private void addPlaceholder2() {
        CustomRecipeRegistry.addMachineRecipe(
                "facadingbench",
                new ItemStack(Blocks.BONE_BLOCK, 4),
                5000,
                new ItemStack(ModRegistry.ITEM_BOXOFNAILS, 1),
                new ItemStack(ModRegistry.ITEM_FRAMEBUNDLE, 8)
        );
    }


    private void addReinforcementBundle() {
        CustomRecipeRegistry.addMachineRecipe(
                "press",
                new ItemStack(ModRegistry.ITEM_REINFORCEMENTBUNDLE, 4),
                5000,
                "ingotIron"
        );

        CustomRecipeRegistry.addMachineRecipe(
                "press",
                new ItemStack(ModRegistry.ITEM_REINFORCEMENTBUNDLE, 4),
                5000,
                "ingotCopper"
        );
    }

    private void addEmeraldPlate() {
        CustomRecipeRegistry.addMachineRecipe(
                "press",
                new ItemStack(ModRegistry.ITEM_EMERALD_PLATE, 1),
                2500,
                "gemEmerald"
        );
    }

    private void addDiamondPlate() {
        CustomRecipeRegistry.addMachineRecipe(
                "press",
                new ItemStack(ModRegistry.ITEM_DIAMOND_PLATE, 1),
                2500,
                "gemDiamond"
        );
    }

    private void addDemerald() {
        CustomRecipeRegistry.addMachineRecipe(
                "press",
                new ItemStack(ModRegistry.ITEM_DEMERALD, 1),
                2500,
                new ItemStack(ModRegistry.ITEM_UNCOMPRESSED_DEMERALD)
        );
    }
}
