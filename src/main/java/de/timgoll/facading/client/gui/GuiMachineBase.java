package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.buttons.ButtonMachinePreview;
import de.timgoll.facading.client.gui.buttons.ButtonMachineSquare;
import de.timgoll.facading.container.ContainerMachineBase;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.facadingbench.PacketGuiAddProductionRequest;
import de.timgoll.facading.network.facadingbench.PacketGuiCancelProductionRequest;
import de.timgoll.facading.network.facadingbench.PacketGuiOpenedRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiMachineBase extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Facading.MODID, "textures/gui/machinefacadingbench.png");
    final int PREVIEWPREV      = 0;
    final int PREVIEWNEXT      = 1;
    final int ADDPRODUCTION    = 2;
    final int CANCELPRODUCTION = 3;
    ButtonMachinePreview previewPrev, previewNext;
    ButtonMachineSquare addProduction, cancelProduction;

    static int itemProductionTicks;
    static double elapsedItemProductionTicks;
    static int itemDisassembleTicks;
    static double elapsedItemDisassembleTicks;
    static int outputBlocks_amount;
    static int outputBlocks_index_producing;
    static int outputBlocks_index;
    static boolean isPowered;
    static boolean isProducing;
    static boolean isDisassembling;

    static int outputBlocks_upperLimit;

    GuiMachineBase(ContainerMachineBase containerBase) {
        super(containerBase);

        //reset all variables
        itemProductionTicks = 0;
        elapsedItemDisassembleTicks = 0;
        itemDisassembleTicks = 0;
        elapsedItemProductionTicks = 0;
        outputBlocks_amount = 0;
        outputBlocks_index_producing = 0;
        outputBlocks_index = 0;

        isPowered = false;
        isProducing = false;
        isDisassembling = false;

        //request data from server
        PacketHandler.INSTANCE.sendToServer(
                new PacketGuiOpenedRequest()
        );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();

        TextureManager mctm = Minecraft.getMinecraft().getTextureManager();

        //draw Gui-Area
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //fixes colorproblem with some overlays (eg JEI)
        mctm.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        drawOverlays();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
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

    /**
     * Enable itemTooltips and update all buttons
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        updateButtons();
    }

    /**
     * Get's fired on every tick. Counts the elapsed ticks, but just if the machine is actually doing something
     */
    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        if (!isPowered)
            return;

        calcTicks();
        handleTickLimits();
    }

    private static void calcTicks() {
        //function is called twice per tick
        if (isProducing)
            elapsedItemProductionTicks+=0.5;

        if (isDisassembling)
            elapsedItemDisassembleTicks+=0.5;
    }

    private static void handleTickLimits() {
        if (itemProductionTicks != 0) {
            if (elapsedItemProductionTicks > itemProductionTicks) {
                isProducing = false;
                elapsedItemProductionTicks = 0;
            }
        }

        if (itemDisassembleTicks != 0) {
            if (elapsedItemDisassembleTicks > itemDisassembleTicks) {
                isDisassembling = false;
                elapsedItemDisassembleTicks = 0;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case PREVIEWPREV:
                outputBlocks_index = (outputBlocks_index > 0) ? outputBlocks_index-1 : 0;
                break;
            case PREVIEWNEXT:
                outputBlocks_index = (outputBlocks_index < outputBlocks_upperLimit-1) ? outputBlocks_index+1 : outputBlocks_upperLimit-1;
                break;
            case ADDPRODUCTION:
                PacketHandler.INSTANCE.sendToServer(
                        new PacketGuiAddProductionRequest(outputBlocks_index)
                );
                break;
            case CANCELPRODUCTION:
                PacketHandler.INSTANCE.sendToServer(
                        new PacketGuiCancelProductionRequest()
                );
                break;
        }
        super.actionPerformed(button);
    }

    void updateButtons() {
        previewPrev.enabled      = outputBlocks_index != 0;
        previewNext.enabled      = outputBlocks_index != outputBlocks_upperLimit-1;
        addProduction.enabled    = outputBlocks_amount == 0 || outputBlocks_index_producing == outputBlocks_index;
        cancelProduction.enabled = outputBlocks_amount != 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /// GUI GRAPHIC FUNCTIONS                                                        ///
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * draws the always changing GUI overlays (not buttons)
     */
    private void drawOverlays() {
        //Powerindicator
        if (isPowered)
            drawTexturedModalRect(guiLeft + 56, guiTop + 59, 195, 59, 10, 10);

        //progress of production
        if (elapsedItemProductionTicks > 0)
            drawTexturedModalRect(guiLeft + 67, guiTop + 30, 176, 30, getProductionProgressPixels(24), 17);

        //progress of disassembly
        if (elapsedItemDisassembleTicks > 0)
            drawTexturedModalRect(guiLeft + 131, guiTop + 59, 176, 59, 19, getDisassembleProgressPixels(18));
    }

    /**
     * helperfunctions to get the correct size
     */
    private int getProductionProgressPixels(int width) {
        if (itemProductionTicks == 0)
            return 0;

        if (outputBlocks_amount == 0)
            return 0;

        return (int) (width * ((float) elapsedItemProductionTicks / (float) itemProductionTicks));
    }

    private int getDisassembleProgressPixels(int height) {
        if (itemDisassembleTicks == 0)
            return 0;

        return (int) (height * ((float) elapsedItemDisassembleTicks / (float) itemDisassembleTicks));
    }

    private void drawSpecialTooltips(ArrayList<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
        //powertooltip
        if (mouseX >= posX && mouseX <= posX + width) {
            if (mouseY >= posY && mouseY <= posY + width) {
                drawHoveringText(lines, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /// NETWORKING STUFF                                                             ///
    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * Function is called, after a client got a response to his datarequest. It sends all the basic data neeeded to
     * display the gui correctly
     * @param _itemProductionTicks needed amount of ticks to produce the current item
     * @param _elapsedItemProductionTicks elapsed amount of ticks in current production
     * @param _itemDisassembleTicks needed amount of ticks to disassemble the current item
     * @param _elapsedItemDisassembleTicks elapsed amount of ticks in current disassembly
     * @param _outputBlocks_amount itemproduction multiplier displayed on top of the arrow
     * @param _outputBlocks_index_producing selected outputblock ID
     * @param _isPowered is the machine powered
     * @param _isProducing is the machine producing
     * @param _isDisassembling is the machine disassembling
     */
    public static void setPacketGuiOpened(int _itemProductionTicks, int _elapsedItemProductionTicks, int _itemDisassembleTicks, int _elapsedItemDisassembleTicks, int _outputBlocks_amount, int _outputBlocks_index_producing, boolean _isPowered, boolean _isProducing, boolean _isDisassembling) {
        itemProductionTicks          = _itemProductionTicks;
        elapsedItemProductionTicks   = (double) _elapsedItemProductionTicks;
        itemDisassembleTicks         = _itemDisassembleTicks;
        elapsedItemDisassembleTicks  = (double) _elapsedItemDisassembleTicks;
        outputBlocks_amount          = _outputBlocks_amount;
        outputBlocks_index_producing = _outputBlocks_index_producing;
        outputBlocks_index           = _outputBlocks_index_producing;
        isPowered                    = _isPowered;
        isProducing                  = _isProducing;
        isDisassembling              = _isDisassembling;
    }

    /**
     * after the client has requested a new production the server has to verify it.
     * The server sends the new calculated production data
     * @param _outputBlocks_amount amount of blocks to produce
     * @param _outputBlocks_index_producing block ID which is produced
     */
    public static void setProduction(int _outputBlocks_amount, int _outputBlocks_index_producing) {
        outputBlocks_amount          = _outputBlocks_amount;
        outputBlocks_index_producing = _outputBlocks_index_producing;
    }

    /**
     * production is started and massaged to GUI. It is necessary to sync the progress bar
     * @param _itemProductionTicks needed ticks to produce item
     */
    public static void startedProduction(int _itemProductionTicks) {
        itemProductionTicks        = _itemProductionTicks;
        elapsedItemProductionTicks = 0; //not needed, but for convenience
        isProducing = true;
    }

    /**
     * disassembly is started and massaged to GUI. It is necessary to sync the progress bar
     * @param _itemDisassembleTicks needed ticks to disassemble item
     */
    public static void startedDisassembly(int _itemDisassembleTicks) {
        itemDisassembleTicks        = _itemDisassembleTicks;
        elapsedItemDisassembleTicks = 0; //not needed, but for convenience
        isDisassembling             = true;
    }

    /**
     * after finishing a production a new message is sent from the sever. It contains the new amount of blocks to produce
     * to keep the gui synced to the server
     * @param _outputBlocks_amount amount of blocks to produce
     */
    public static void finishedProduction(int _outputBlocks_amount) {
        outputBlocks_amount        = _outputBlocks_amount;
    }

    /**
     * everytime the powerstatus of the machine is changed, the gui is informed. This way it is easy to stop the progressbar
     * and the powerindicator if the energysource is lost
     * @param _isPowered true if the machine is powered, false if not
     */
    public static void setPowered(boolean _isPowered) {
        isPowered = _isPowered;
    }
}
