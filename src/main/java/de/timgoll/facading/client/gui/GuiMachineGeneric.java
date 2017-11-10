package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.container.ContainerMachineBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.util.ArrayList;

public class GuiMachineGeneric extends GuiMachineBase {
    GuiMachineGeneric(ContainerMachineBase containerMachineBase) {
        super(containerMachineBase);
        xSize = 176;
        ySize = 155;

        texture = new ResourceLocation(Facading.MODID, "textures/gui/machinegeneric.png");

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
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, 62, Color.darkGray.getRGB()); //vanilla name

        ArrayList<String> lines = new ArrayList<>();
        lines.add( new TextComponentTranslation("machine.status.power." + isPowered).getFormattedText() );
        drawSpecialTooltips(lines, mouseX, mouseY, guiLeft + 20, guiTop + 31, 10, 10);

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}