package de.timgoll.facadingIndustry.network.facadingbench;

import de.timgoll.facadingIndustry.client.gui.GuiMachineBase;
import de.timgoll.facadingIndustry.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiStartedDisassembly implements IMessage {

    //data to transmit

    private int disassemblyTicks;

    //data to transmit END

    public PacketGuiStartedDisassembly() {}

    public PacketGuiStartedDisassembly(int productionTicks) {
        this.disassemblyTicks = productionTicks;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.disassemblyTicks);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.disassemblyTicks = buf.readInt();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiStartedDisassembly, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiStartedDisassembly message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiStartedDisassembly message) {
            GuiMachineBase.startedDisassembly(message.disassemblyTicks);
        }
    }

}
