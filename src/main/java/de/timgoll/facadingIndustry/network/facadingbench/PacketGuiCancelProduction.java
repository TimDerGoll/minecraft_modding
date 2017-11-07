package de.timgoll.facadingIndustry.network.facadingbench;

import de.timgoll.facadingIndustry.client.gui.GuiMachineBase;
import de.timgoll.facadingIndustry.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiCancelProduction implements IMessage {

    //data to transmit

    private int outputBlocks_amount;
    private int outputBlocks_index_producing;

    //data to transmit END

    public PacketGuiCancelProduction() {}

    public PacketGuiCancelProduction(int outputBlocks_amount, int outputBlocks_index_producing) {
        this.outputBlocks_amount = outputBlocks_amount;
        this.outputBlocks_index_producing = outputBlocks_index_producing;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.outputBlocks_amount);
        buf.writeInt(this.outputBlocks_index_producing);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            this.outputBlocks_amount = buf.readInt();
            this.outputBlocks_index_producing = buf.readInt();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiCancelProduction, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiCancelProduction message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiCancelProduction message) {
            GuiMachineBase.setProduction(message.outputBlocks_amount, message.outputBlocks_index_producing);
        }
    }

}
