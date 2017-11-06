package de.timgoll.facading.misc;

import de.timgoll.facading.init.ModRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

        addPress();
        addFacadingbench();



        addFacade();
        addPlaceholder();
        addPlaceholder2();
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











    // CUSTOM CRAFTING METHODS
    private void addFacade() {
        RecipeHandlerFacadingBench.addRecipe(
                new ItemStack(ModRegistry.BLOCK_FACADE, 8),
                new ArrayList<ItemStack>() {
                    {
                        add(new ItemStack(ModRegistry.ITEM_BOXOFNAILS, 1));
                        add(new ItemStack(ModRegistry.ITEM_FRAMEBUNDLE, 8));
                    }
                },
                100
        );
    }

    private void addPlaceholder() {
        RecipeHandlerFacadingBench.addRecipe(
                new ItemStack(Blocks.BOOKSHELF, 8),
                new ArrayList<ItemStack>() {
                    {
                        add(new ItemStack(ModRegistry.ITEM_BOXOFNAILS, 1));
                        add(new ItemStack(ModRegistry.ITEM_FRAMEBUNDLE, 8));
                    }
                },
                1000
        );
    }

    private void addPlaceholder2() {
        RecipeHandlerFacadingBench.addRecipe(
                new ItemStack(Blocks.STONE, 8),
                new ArrayList<ItemStack>() {
                    {
                        add(new ItemStack(ModRegistry.ITEM_BOXOFNAILS, 1));
                        add(new ItemStack(ModRegistry.ITEM_FRAMEBUNDLE, 8));
                    }
                },
                10
        );
    }
}
