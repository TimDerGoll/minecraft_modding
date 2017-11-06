package de.timgoll.facading.network.facadingbench;

import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.container.ContainerMachineFacadingbench;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiCancelProductionRequest implements IMessage {

    //data to transmit

    private boolean productionCanceled;

    //data to transmit END

    public PacketGuiCancelProductionRequest() {
        this.productionCanceled = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.productionCanceled);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.productionCanceled = buf.readBoolean();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiCancelProductionRequest, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiCancelProductionRequest message, MessageContext ctx) {
            if (ctx.side != Side.SERVER)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiCancelProductionRequest message) {

            ContainerMachineFacadingbench openContainer = (ContainerMachineFacadingbench) GuiHandler.getOpenContainer();
            if (openContainer == null)
                return;

            openContainer.productionCanceled();

        }
    }

}
