package de.timgoll.facading.client.gui;

import de.timgoll.facading.Facading;
import de.timgoll.facading.client.gui.buttons.GuiFacadingbenchButtons_preview;
import de.timgoll.facading.client.gui.buttons.GuiFacadingbenchButtons_square;
import de.timgoll.facading.container.ContainerFacadingbench;
import de.timgoll.facading.misc.RecipeHandlerFacadingBench;
import de.timgoll.facading.network.PacketHandler;
import de.timgoll.facading.network.facadingbench.PacketGuiAddProductionRequest;
import de.timgoll.facading.network.facadingbench.PacketGuiCancelProductionRequest;
import de.timgoll.facading.network.facadingbench.PacketGuiOpenedRequest;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;

public class GuiFacadingbenchContainer extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Facading.MODID, "textures/gui/facadingbench.png");

    private int topleftX, topleftY;

    final int PREVIEWPREV      = 0;
    final int PREVIEWNEXT      = 1;
    final int ADDPRODUCTION    = 2;
    final int CANCELPRODUCTION = 3;

    GuiFacadingbenchButtons_preview previewPrev, previewNext;
    GuiFacadingbenchButtons_square addProduction, cancelProduction;

    private static long ticks;

    //GUI changing values

    private int itemProductionTicks;
    private int itemProductionMultiplier;
    private int elapsedItemProductionTicks;
    private int itemDisassembleTicks;
    private int elapsedItemDisassembleTicks;
    private int outputBlocks_amount;
    private int outputBlocks_index_producing;
    private boolean isPowered;

    private long productionStartedTicks;

    //GUI changing values END

    private int outputBlocks_index = 0;
    private World world;


    public GuiFacadingbenchContainer(InventoryPlayer inventoryPlayer, TileBlockFacadingbench tileBlockFacadingbench) {
        super(new ContainerFacadingbench(inventoryPlayer, tileBlockFacadingbench));
        xSize = 176;
        ySize = 193;

        world = inventoryPlayer.player.world;
        if (world.isRemote) {
            PacketHandler.INSTANCE.sendToServer(
                    new PacketGuiOpenedRequest()
            );
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(new TextComponentTranslation("tile.facading.facadingbench.name").getFormattedText(), 8, 6, Color.darkGray.getRGB());
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, 100, Color.darkGray.getRGB()); //vanilla name

        fontRenderer.drawString("x" + this.outputBlocks_amount, 64, 28, Color.darkGray.getRGB());

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        TextureManager mctm = Minecraft.getMinecraft().getTextureManager();

        drawDefaultBackground();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //fixes colorproblem with some overlays (eg JEI)
        mctm.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (this.isPowered) {
            drawTexturedModalRect(guiLeft + 56, guiTop + 59, 195, 59, 10, 10);
        }

        if (this.outputBlocks_amount > 0)
            this.elapsedItemProductionTicks = (int) (ticks - this.productionStartedTicks);

        drawTexturedModalRect(guiLeft + 68, guiTop + 30, 177, 30, getProductionProgressPixels(22, this.itemProductionTicks, this.elapsedItemProductionTicks), 17);

        //draw Preview
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
        topleftX = ( width  - xSize ) / 2;
        topleftY = ( height - ySize ) / 2;

        buttonList.clear();
        buttonList.add( previewPrev      = new GuiFacadingbenchButtons_preview(PREVIEWPREV, topleftX + 7, topleftY +  18, 32, 193, 7,36));
        buttonList.add( previewNext      = new GuiFacadingbenchButtons_preview(PREVIEWNEXT, topleftX + 54, topleftY +  18, 53, 193, 7,36));

        buttonList.add( addProduction    = new GuiFacadingbenchButtons_square(ADDPRODUCTION, topleftX + 17, topleftY +  56, 0, 193, 16,16));
        buttonList.add( cancelProduction = new GuiFacadingbenchButtons_square(CANCELPRODUCTION, topleftX + 35, topleftY +  56, 16, 193, 16,16));

        updateButtons();

        super.initGui();
    }

    @SubscribeEvent
    public static void serverTickEvent(TickEvent.ClientTickEvent event) {
        ticks++;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case PREVIEWPREV:
                outputBlocks_index = (outputBlocks_index > 0) ? outputBlocks_index-1 : 0;
                break;
            case PREVIEWNEXT:
                outputBlocks_index = (outputBlocks_index < RecipeHandlerFacadingBench.size()-1) ? outputBlocks_index+1 : RecipeHandlerFacadingBench.size()-1;
                break;
            case ADDPRODUCTION:
                if (world.isRemote) {
                    PacketHandler.INSTANCE.sendToServer(
                        new PacketGuiAddProductionRequest(outputBlocks_index)
                    );
                }
                break;
            case CANCELPRODUCTION:
                if (world.isRemote) {
                    PacketHandler.INSTANCE.sendToServer(
                        new PacketGuiCancelProductionRequest()
                    );
                }
                break;
        }
        updateButtons();
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    private void updateButtons() {
        previewPrev.enabled      = outputBlocks_index != 0;
        previewNext.enabled      = outputBlocks_index != RecipeHandlerFacadingBench.size()-1;
        addProduction.enabled    = outputBlocks_amount == 0 || outputBlocks_index_producing == outputBlocks_index;
        cancelProduction.enabled = outputBlocks_amount != 0;
    }

    private int getProductionProgressPixels(int width, int itemProductionTicks, int elapsedItemProductionTicks) {
        if (itemProductionTicks == 0)
            return 0;

        if (this.outputBlocks_amount == 0)
            return 0;

        return (int) (width * ((float) elapsedItemProductionTicks / (float) itemProductionTicks) / 2); //TODO why devide by 2?
    }



    //NETWORKING STUFF

    public void setPacketGuiOpened(int itemProductionTicks, int elapsedItemProductionTicks, int itemDisassembleTicks, int elapsedItemDisassembleTicks, int outputBlocks_amount, int outputBlocks_index_producing, boolean isPowered) {
        this.itemProductionTicks          = itemProductionTicks;
        this.elapsedItemProductionTicks   = elapsedItemProductionTicks;
        this.itemDisassembleTicks         = itemDisassembleTicks;
        this.elapsedItemDisassembleTicks  = elapsedItemDisassembleTicks;
        this.outputBlocks_amount          = outputBlocks_amount;
        this.outputBlocks_index_producing = outputBlocks_index_producing;
        this.outputBlocks_index           = outputBlocks_index_producing;
        this.isPowered                    = isPowered;

        this.productionStartedTicks     = ticks - this.elapsedItemProductionTicks;

        updateButtons();
    }

    public void setProduction(int multiplier, int outputBlocks_index_producing) {
        this.outputBlocks_amount = multiplier;
        this.outputBlocks_index_producing = outputBlocks_index_producing;

        updateButtons();
    }

    public void startedProduction(int productionTicks) {
        this.itemProductionTicks        = productionTicks;
        this.elapsedItemProductionTicks = 0;

        this.productionStartedTicks     = ticks;

        updateButtons();
    }

    public void finishedProduction(int outputBlocks_amount) {
        this.outputBlocks_amount        = outputBlocks_amount;
        this.elapsedItemProductionTicks = 0;

        updateButtons();
    }



}
