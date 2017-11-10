package de.timgoll.facading.network.packets;

import de.timgoll.facading.client.gui.GuiMachineBase;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiIsPowered implements IMessage {

    //data to transmit

    private boolean isWorking;
    private long posX;
    private long posY;
    private long posZ;

    //data to transmit END

    public PacketGuiIsPowered() {}

    public PacketGuiIsPowered(boolean isWorking, long posX, long posY, long posZ) {
        this.isWorking = isWorking;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isWorking);
        buf.writeLong(this.posX);
        buf.writeLong(this.posY);
        buf.writeLong(this.posZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.isWorking = buf.readBoolean();
            this.posX = buf.readLong();
            this.posY = buf.readLong();
            this.posZ = buf.readLong();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiIsPowered, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiIsPowered message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiIsPowered message) {
            GuiMachineBase.setPowered(message.isWorking, message.posX, message.posY, message.posZ);
        }
    }

}
