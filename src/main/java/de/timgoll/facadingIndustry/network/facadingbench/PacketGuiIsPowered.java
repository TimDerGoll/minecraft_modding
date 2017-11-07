package de.timgoll.facadingIndustry.network.facadingbench;

import de.timgoll.facadingIndustry.client.gui.GuiMachineBase;
import de.timgoll.facadingIndustry.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiIsPowered implements IMessage {

    //data to transmit

    private boolean isWorking;

    //data to transmit END

    public PacketGuiIsPowered() {}

    public PacketGuiIsPowered(boolean isWorking) {
        this.isWorking = isWorking;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isWorking);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            this.isWorking = buf.readBoolean();

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
            GuiMachineBase.setPowered(message.isWorking);
        }
    }

}
