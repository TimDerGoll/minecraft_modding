package de.timgoll.facading.network.packets;

import de.timgoll.facading.client.gui.GuiMachineBase;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PackedGuiFinishedProduction implements IMessage {

    //data to transmit

    private int outputBlocks_amount;
    private long posX;
    private long posY;
    private long posZ;

    //data to transmit END

    public PackedGuiFinishedProduction() {}

    public PackedGuiFinishedProduction(int outputBlocks_amount, long posX, long posY, long posZ) {
        this.outputBlocks_amount = outputBlocks_amount;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.outputBlocks_amount);
        buf.writeLong(this.posX);
        buf.writeLong(this.posY);
        buf.writeLong(this.posZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.outputBlocks_amount = buf.readInt();
            this.posX = buf.readLong();
            this.posY = buf.readLong();
            this.posZ = buf.readLong();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PackedGuiFinishedProduction, IMessage> {
        @Override
        public IMessage onMessage(PackedGuiFinishedProduction message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PackedGuiFinishedProduction message) {
            GuiMachineBase.finishedProduction(message.outputBlocks_amount, message.posX, message.posY, message.posZ);
        }
    }
}
