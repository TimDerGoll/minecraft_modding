package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.container.ContainerMachinePress;
import de.timgoll.facading.titleentities.TileBlockMachinePress;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.util.ArrayList;

public class GuiMachinePress extends GuiMachineBase {
    GuiMachinePress(InventoryPlayer inventoryPlayer, TileBlockMachinePress tileBlockMachinePress) {
        super(new ContainerMachinePress(inventoryPlayer, tileBlockMachinePress));
        xSize = 176;
        ySize = 155;

        texture = new ResourceLocation(Facading.MODID, "textures/gui/machinebase.png");

        powerIndicatorLeft = 20;
        powerIndicatorTop = 31;
        powerIndicatorTextureLeft = 200;
        powerIndicatorTextureTop = 31;

        progressBarLeft = 67;
        progressBarTop = 28;
        progressBarTextureLeft = 176;
        progressBarTextureTop = 28;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.press.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, 62, Color.darkGray.getRGB()); //vanilla name

        ArrayList<String> lines = new ArrayList<>();
        lines.add( new TextComponentTranslation("machine.status.power." + isPowered).getFormattedText() );
        drawSpecialTooltips(lines, mouseX, mouseY, guiLeft + 20, guiTop + 31, 10, 10);

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
