package de.timgoll.facading.client.gui;

import de.timgoll.facading.container.ContainerMachineSawmill;
import de.timgoll.facading.titleentities.TileBlockMachineSawmill;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;

public class GuiMachineSawmill extends GuiMachineGeneric {
    GuiMachineSawmill(InventoryPlayer inventoryPlayer, TileBlockMachineSawmill tileBlockMachineSawmill) {
        super(new ContainerMachineSawmill(inventoryPlayer, tileBlockMachineSawmill));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.sawmill.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
