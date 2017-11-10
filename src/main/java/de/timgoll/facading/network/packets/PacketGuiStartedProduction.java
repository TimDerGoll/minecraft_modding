package de.timgoll.facading.network.packets;

import de.timgoll.facading.client.gui.GuiMachineBase;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiStartedProduction implements IMessage {

    //data to transmit

    private int productionTicks;
    private long posX;
    private long posY;
    private long posZ;

    //data to transmit END

    public PacketGuiStartedProduction() {}

    public PacketGuiStartedProduction(int productionTicks, long posX, long posY, long posZ) {
        this.productionTicks = productionTicks;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.productionTicks);
        buf.writeLong(this.posX);
        buf.writeLong(this.posY);
        buf.writeLong(this.posZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.productionTicks = buf.readInt();
            this.posX = buf.readLong();
            this.posY = buf.readLong();
            this.posZ = buf.readLong();

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
            GuiMachineBase.startedProduction(message.productionTicks, message.posX, message.posY, message.posZ);
        }
    }
}
