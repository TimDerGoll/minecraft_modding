package de.timgoll.facading.network.packets;

import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.container.ContainerMachineBase;
import de.timgoll.facading.container.ContainerMachineFacadingbench;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiOpenedRequest implements IMessage {

    //data to transmit

    private boolean guiOpened;

    //data to transmit END

    public PacketGuiOpenedRequest() {
        this.guiOpened = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.guiOpened);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.guiOpened = buf.readBoolean();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiOpenedRequest, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiOpenedRequest message, MessageContext ctx) {
            if (ctx.side != Side.SERVER)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiOpenedRequest message) {

            ContainerMachineBase openContainer = (ContainerMachineBase) GuiHandler.getOpenContainer();
            if (openContainer == null)
                return;

            openContainer.guiOpened();

        }
    }
}
