package de.timgoll.facading.client.gui;

import de.timgoll.facading.container.ContainerMachinePress;
import de.timgoll.facading.titleentities.TileBlockMachinePress;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;

public class GuiMachinePress extends GuiMachineGeneric {
    GuiMachinePress(InventoryPlayer inventoryPlayer, TileBlockMachinePress tileBlockMachinePress) {
        super(new ContainerMachinePress(inventoryPlayer, tileBlockMachinePress));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.press.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
