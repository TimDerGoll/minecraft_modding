package de.timgoll.facading.network.facadingbench;

import de.timgoll.facading.client.gui.GuiFacadingbenchContainer;
import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiOpened implements IMessage {

    private boolean isValid;

    //data to transmit

    private int itemProduceTicks;
    private int elapsedItemProduceTicks;
    private int itemDisassembleTicks;
    private int elapsedItemDisassembleTicks;
    private int itemsMultiplier;
    private int outputBlocks_index_producing;
    private boolean isPowered;

    //data to transmit END

    //called, if no data is attached
    public PacketGuiOpened() {
        this.isValid = false;
    }

    /**
     * This method is called by the server and transmits the initial data to the client GUI
     * @param itemProduceTicks ticks needed to produce one ItemStack
     * @param elapsedItemProduceTicks ticks elapsed since this production started
     * @param itemDisassembleTicks ticks needed to disassemble ItemStack
     * @param elapsedItemDisassembleTicks ticks elapsed since disassbly started
     * @param itemsMultiplier how much to produce
     */
    public PacketGuiOpened(int itemProduceTicks, int elapsedItemProduceTicks, int itemDisassembleTicks, int elapsedItemDisassembleTicks, int itemsMultiplier, int outputBlocks_index_producing, boolean isPowered) {
        this.itemProduceTicks             = itemProduceTicks;
        this.elapsedItemProduceTicks      = elapsedItemProduceTicks;
        this.itemDisassembleTicks         = itemDisassembleTicks;
        this.elapsedItemDisassembleTicks  = elapsedItemDisassembleTicks;
        this.itemsMultiplier              = itemsMultiplier;
        this.outputBlocks_index_producing = outputBlocks_index_producing;
        this.isPowered                    = isPowered;

        this.isValid = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (!this.isValid)
            return;
        buf.writeInt(this.itemProduceTicks);
        buf.writeInt(this.elapsedItemProduceTicks);
        buf.writeInt(this.itemDisassembleTicks);
        buf.writeInt(this.elapsedItemDisassembleTicks);
        buf.writeInt(this.itemsMultiplier);
        buf.writeInt(this.outputBlocks_index_producing);
        buf.writeBoolean(this.isPowered);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.itemProduceTicks             = buf.readInt();
            this.elapsedItemProduceTicks      = buf.readInt();
            this.itemDisassembleTicks         = buf.readInt();
            this.elapsedItemDisassembleTicks  = buf.readInt();
            this.itemsMultiplier              = buf.readInt();
            this.outputBlocks_index_producing = buf.readInt();
            this.isPowered                    = buf.readBoolean();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
            return;
        }
        this.isValid = true;
    }


    public static class Handler implements IMessageHandler<PacketGuiOpened, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiOpened message, MessageContext ctx) {
            if (!message.isValid && ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiOpened message) {

            GuiFacadingbenchContainer openGui = (GuiFacadingbenchContainer) GuiHandler.getOpenGui();
            if (openGui == null)
                return;

            openGui.setPacketGuiOpened(
                    message.itemProduceTicks,
                    message.elapsedItemProduceTicks,
                    message.itemDisassembleTicks,
                    message.elapsedItemDisassembleTicks,
                    message.itemsMultiplier,
                    message.outputBlocks_index_producing,
                    message.isPowered
            );

        }
    }

}
