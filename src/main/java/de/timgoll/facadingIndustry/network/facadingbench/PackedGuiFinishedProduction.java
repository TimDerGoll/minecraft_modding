package de.timgoll.facadingIndustry.network.facadingbench;

import de.timgoll.facadingIndustry.client.gui.GuiMachineBase;
import de.timgoll.facadingIndustry.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PackedGuiFinishedProduction implements IMessage {

    //data to transmit

    private int outputBlocks_amount;

    //data to transmit END

    public PackedGuiFinishedProduction() {}

    public PackedGuiFinishedProduction(int outputBlocks_amount) {
        this.outputBlocks_amount = outputBlocks_amount;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.outputBlocks_amount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.outputBlocks_amount = buf.readInt();

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
            GuiMachineBase.finishedProduction(message.outputBlocks_amount);
        }
    }
}
