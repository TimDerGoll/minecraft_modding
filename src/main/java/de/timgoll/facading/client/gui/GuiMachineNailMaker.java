package de.timgoll.facading.client.gui;

import de.timgoll.facading.container.ContainerMachineNailMaker;
import de.timgoll.facading.titleentities.TileBlockMachineNailMaker;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;

public class GuiMachineNailMaker extends GuiMachineGeneric {
    GuiMachineNailMaker(InventoryPlayer inventoryPlayer, TileBlockMachineNailMaker tileBlockMachineNailMaker) {
        super(new ContainerMachineNailMaker(inventoryPlayer, tileBlockMachineNailMaker));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.nailmaker.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
