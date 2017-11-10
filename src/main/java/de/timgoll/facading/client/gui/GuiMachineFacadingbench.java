package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.buttons.ButtonMachinePreview;
import de.timgoll.facading.client.gui.buttons.ButtonMachineSquare;
import de.timgoll.facading.container.ContainerMachineFacadingbench;
import de.timgoll.facading.misc.CustomRecipeRegistry;
import de.timgoll.facading.titleentities.TileBlockMachineFacadingbench;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.util.ArrayList;

public class GuiMachineFacadingbench extends GuiMachineBase {

    public GuiMachineFacadingbench(InventoryPlayer inventoryPlayer, TileBlockMachineFacadingbench tileBlockFacadingbench) {
        //setting up default GUI
        super(new ContainerMachineFacadingbench(inventoryPlayer, tileBlockFacadingbench));
        xSize = 176;
        ySize = 193;

        texture = new ResourceLocation(Facading.MODID, "textures/gui/machinefacadingbench.png");

        powerIndicatorLeft = 56;
        powerIndicatorTop = 59;
        powerIndicatorTextureLeft = 195;
        powerIndicatorTextureTop = 59;

        progressBarLeft = 67;
        progressBarTop = 30;
        progressBarTextureLeft = 176;
        progressBarTextureTop = 30;

        //set up limits for navigationarea
        outputBlocks_upperLimit = CustomRecipeRegistry.getRecipeList("facadingbench").size();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.facadingbench.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, 100, Color.darkGray.getRGB()); //vanilla name

        fontRenderer.drawString("x" + outputBlocks_amount, 64, 28, Color.darkGray.getRGB());

        ArrayList<String> lines = new ArrayList<>();
        lines.add( new TextComponentTranslation("machine.status.power." + isPowered).getFormattedText() );
        drawSpecialTooltips(lines, mouseX, mouseY, guiLeft + 56, guiTop + 59, 10, 10);

        lines.clear();
        lines.add( new TextComponentTranslation("machine.button.add").getFormattedText() );
        drawSpecialTooltips(lines, mouseX, mouseY, guiLeft + 17, guiTop + 56, 16, 16);

        lines.clear();
        lines.add( new TextComponentTranslation("machine.button.cancel").getFormattedText() );
        drawSpecialTooltips(lines, mouseX, mouseY, guiLeft + 35, guiTop + 56, 16, 16);

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
            mc.getRenderItem().renderItemIntoGUI(CustomRecipeRegistry.getOutputList("facadingbench").get(outputBlocks_index), 0, 0);
        }
        GlStateManager.popMatrix();
        mc.getRenderItem().renderItemOverlays(fontRenderer, CustomRecipeRegistry.getOutputList("facadingbench").get(outputBlocks_index), guiLeft + 34, guiTop + 36);
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

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        updateButtons();
    }
}
