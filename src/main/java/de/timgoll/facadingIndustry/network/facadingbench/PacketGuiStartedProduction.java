package de.timgoll.facadingIndustry.network.facadingbench;

import de.timgoll.facadingIndustry.client.gui.GuiMachineBase;
import de.timgoll.facadingIndustry.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiStartedProduction implements IMessage {

    //data to transmit

    private int productionTicks;

    //data to transmit END

    public PacketGuiStartedProduction() {}

    public PacketGuiStartedProduction(int productionTicks) {
        this.productionTicks = productionTicks;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.productionTicks);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.productionTicks = buf.readInt();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiStartedProduction, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiStartedProduction message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiStartedProduction message) {
            GuiMachineBase.startedProduction(message.productionTicks);
        }
    }
}
