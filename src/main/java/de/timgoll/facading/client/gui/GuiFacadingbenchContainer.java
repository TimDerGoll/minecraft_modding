package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.container.ContainerFacadingbench;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiFacadingbenchContainer extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Facading.MODID, "textures/gui/facadingbench.png");

    public GuiFacadingbenchContainer(InventoryPlayer player, TileBlockFacadingbench tileBlockFacadingbench) {
        super(new ContainerFacadingbench(player, tileBlockFacadingbench));
        xSize = 176;
        ySize = 193;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //fontRenderer.drawString(new TextComponentTranslation("tile.tutorial_container.name").getFormattedText(), 5, 5, Color.darkGray.getRGB());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

}
