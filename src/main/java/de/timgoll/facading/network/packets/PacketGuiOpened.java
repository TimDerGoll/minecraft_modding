package de.timgoll.facading.network.packets;

import de.timgoll.facading.client.gui.GuiMachineBase;
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
    private int outputBlocks_amount;
    private int outputBlocks_index_producing;
    private boolean isPowered;
    private boolean isWorking;
    private boolean isDisassembling;

    private boolean isGeneric;

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
     * @param outputBlocks_amount how much to produce
     */
    public PacketGuiOpened(int itemProduceTicks, int elapsedItemProduceTicks, int itemDisassembleTicks, int elapsedItemDisassembleTicks, int outputBlocks_amount, int outputBlocks_index_producing, boolean isPowered, boolean isWorking, boolean isDisassembling) {
        this.itemProduceTicks             = itemProduceTicks;
        this.elapsedItemProduceTicks      = elapsedItemProduceTicks;
        this.itemDisassembleTicks         = itemDisassembleTicks;
        this.elapsedItemDisassembleTicks  = elapsedItemDisassembleTicks;
        this.outputBlocks_amount          = outputBlocks_amount;
        this.outputBlocks_index_producing = outputBlocks_index_producing;
        this.isPowered                    = isPowered;
        this.isWorking                    = isWorking;
        this.isDisassembling              = isDisassembling;

        this.isValid = true;
        this.isGeneric = false;
    }

    public PacketGuiOpened(int itemProduceTicks, int elapsedItemProduceTicks, boolean isPowered, boolean isWorking) {
        this.itemProduceTicks             = itemProduceTicks;
        this.elapsedItemProduceTicks      = elapsedItemProduceTicks;
        this.isPowered                    = isPowered;
        this.isWorking                    = isWorking;

        this.isValid = true;
        this.isGeneric = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (!this.isValid)
            return;
        buf.writeInt(this.itemProduceTicks);
        buf.writeInt(this.elapsedItemProduceTicks);
        buf.writeBoolean(this.isPowered);
        buf.writeBoolean(this.isWorking);
        buf.writeBoolean(this.isGeneric);

        if (this.isGeneric)
            return;

        buf.writeInt(this.itemDisassembleTicks);
        buf.writeInt(this.elapsedItemDisassembleTicks);
        buf.writeInt(this.outputBlocks_amount);
        buf.writeInt(this.outputBlocks_index_producing);
        buf.writeBoolean(this.isDisassembling);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.itemProduceTicks             = buf.readInt();
            this.elapsedItemProduceTicks      = buf.readInt();
            this.isPowered                    = buf.readBoolean();
            this.isWorking                    = buf.readBoolean();
            this.isGeneric                    = buf.readBoolean();

            if (this.isGeneric)
                return;

            this.itemDisassembleTicks         = buf.readInt();
            this.elapsedItemDisassembleTicks  = buf.readInt();
            this.outputBlocks_amount          = buf.readInt();
            this.outputBlocks_index_producing = buf.readInt();
            this.isDisassembling              = buf.readBoolean();

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

            if (message.isGeneric) {
                GuiMachineBase.setPacketGuiOpened(
                        message.itemProduceTicks,
                        message.elapsedItemProduceTicks,
                        message.isPowered,
                        message.isWorking
                );
            } else {
                GuiMachineBase.setPacketGuiOpened(
                        message.itemProduceTicks,
                        message.elapsedItemProduceTicks,
                        message.itemDisassembleTicks,
                        message.elapsedItemDisassembleTicks,
                        message.outputBlocks_amount,
                        message.outputBlocks_index_producing,
                        message.isPowered,
                        message.isWorking,
                        message.isDisassembling
                );
            }
        }
    }

}
