package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.buttons.GuiFacadingbenchButtons_preview;
import de.timgoll.facading.client.gui.buttons.GuiFacadingbenchButtons_square;
import de.timgoll.facading.container.ContainerFacadingbench;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.Sys;

import java.awt.*;

public class GuiFacadingbenchContainer extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Facading.MODID, "textures/gui/facadingbench.png");

    private int topleftX, topleftY;

    final int PREVIEWPREV      = 0;
    final int PREVIEWNEXT      = 1;
    final int ADDPRODUCTION    = 2;
    final int CANCELPRODUCTION = 3;

    GuiFacadingbenchButtons_preview previewPrev, previewNext;
    GuiFacadingbenchButtons_square addProduction, cancalProduction;

    //GUI changing values

    private int itemProduceTicks;
    private int elapsedItemProduceTicks;
    private int itemDisassembleTicks;
    private int elapsedItemDisassembleTicks;
    private int itemsMultiplier;
    private boolean isPowered;

    //GUI changing values END

    public GuiFacadingbenchContainer(InventoryPlayer player, TileBlockFacadingbench tileBlockFacadingbench) {
        super(new ContainerFacadingbench(player, tileBlockFacadingbench));
        xSize = 176;
        ySize = 193;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.facadingbench.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, 100, Color.darkGray.getRGB()); //vanilla name

        fontRenderer.drawString("x0", 65, 27, Color.darkGray.getRGB());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //fixes colorproblem with some overlays (eg JEI)
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void initGui() {
        topleftX = ( width  - xSize ) / 2;
        topleftY = ( height - ySize ) / 2;

        buttonList.clear();
        buttonList.add( previewPrev      = new GuiFacadingbenchButtons_preview(PREVIEWPREV, topleftX + 54, topleftY +  18, 67, 193, 60,193));
        buttonList.add( previewNext      = new GuiFacadingbenchButtons_preview(PREVIEWNEXT, topleftX + 7, topleftY +  18, 46, 193, 39,193));
        buttonList.add( addProduction    = new GuiFacadingbenchButtons_square(ADDPRODUCTION, topleftX + 17, topleftY +  56, 0, 225, 0,209));
        buttonList.add( cancalProduction = new GuiFacadingbenchButtons_square(CANCELPRODUCTION, topleftX + 35, topleftY +  56, 16, 225, 16,209));
        super.initGui();
    }

    public void setPacketGuiOpened(int itemProduceTicks, int elapsedItemProduceTicks, int itemDisassembleTicks, int elapsedItemDisassembleTicks, int itemsMultiplier, boolean isPowered) {
        this.itemProduceTicks            = itemProduceTicks;
        this.elapsedItemProduceTicks     = elapsedItemProduceTicks;
        this.itemDisassembleTicks        = itemDisassembleTicks;
        this.elapsedItemDisassembleTicks = elapsedItemDisassembleTicks;
        this.itemsMultiplier             = itemsMultiplier;
        this.isPowered                   = isPowered;

        System.out.println("Message recieved, Station is powered: " + isPowered + "............................................................................");
    }




}
