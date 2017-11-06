package de.timgoll.facading.client.gui;

import de.timgoll.facading.client.gui.buttons.ButtonMachinePreview;
import de.timgoll.facading.client.gui.buttons.ButtonMachineSquare;
import de.timgoll.facading.container.ContainerMachineFacadingbench;
import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import de.timgoll.facading.titleentities.TileBlockMachineFacadingbench;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;

public class GuiMachineFacadingbench extends GuiMachineBase {

    public GuiMachineFacadingbench(InventoryPlayer inventoryPlayer, TileBlockMachineFacadingbench tileBlockFacadingbench) {
        //setting up default GUI
        super(new ContainerMachineFacadingbench(inventoryPlayer, tileBlockFacadingbench));
        xSize = 176;
        ySize = 193;

        //set up limits for navigationarea
        outputBlocks_upperLimit = RecipeHandlerFacadingBench.size();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.facadingbench.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, 100, Color.darkGray.getRGB()); //vanilla name

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        //draw productionpreview icon
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(guiLeft + 18, guiTop + 20, 0);
            GlStateManager.scale(2, 2, 2);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemIntoGUI(RecipeHandlerFacadingBench.outputStack.get(outputBlocks_index), 0, 0);
        }
        GlStateManager.popMatrix();
        mc.getRenderItem().renderItemOverlays(fontRenderer, RecipeHandlerFacadingBench.outputStack.get(outputBlocks_index), guiLeft + 34, guiTop + 36);
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();
        buttonList.add( previewPrev      = new ButtonMachinePreview(PREVIEWPREV, guiLeft + 7, guiTop +  18, 32, 193, 7,36));
        buttonList.add( previewNext      = new ButtonMachinePreview(PREVIEWNEXT, guiLeft + 54, guiTop +  18, 53, 193, 7,36));

        buttonList.add( addProduction    = new ButtonMachineSquare(ADDPRODUCTION, guiLeft + 17, guiTop +  56, 0, 193, 16,16));
        buttonList.add( cancelProduction = new ButtonMachineSquare(CANCELPRODUCTION, guiLeft + 35, guiTop +  56, 16, 193, 16,16));

        updateButtons();
    }

}
