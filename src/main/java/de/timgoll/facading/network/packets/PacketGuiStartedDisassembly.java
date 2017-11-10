package de.timgoll.facading.network.packets;

import de.timgoll.facading.client.gui.GuiMachineBase;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiStartedDisassembly implements IMessage {

    //data to transmit

    private int disassemblyTicks;
    private long posX;
    private long posY;
    private long posZ;

    //data to transmit END

    public PacketGuiStartedDisassembly() {}

    public PacketGuiStartedDisassembly(int productionTicks, long posX, long posY, long posZ) {
        this.disassemblyTicks = productionTicks;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.disassemblyTicks);
        buf.writeLong(this.posX);
        buf.writeLong(this.posY);
        buf.writeLong(this.posZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.disassemblyTicks = buf.readInt();
            this.posX = buf.readLong();
            this.posY = buf.readLong();
            this.posZ = buf.readLong();

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
            GuiMachineBase.startedDisassembly(message.disassemblyTicks, message.posX, message.posY, message.posZ);
        }
    }

}
