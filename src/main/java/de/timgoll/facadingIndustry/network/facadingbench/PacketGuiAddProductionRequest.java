package de.timgoll.facadingIndustry.network.facadingbench;

import de.timgoll.facadingIndustry.client.gui.GuiHandler;
import de.timgoll.facadingIndustry.container.ContainerMachineFacadingbench;
import de.timgoll.facadingIndustry.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiAddProductionRequest implements IMessage {

    //data to transmit

    private int outputBlocks_index_producing;

    //data to transmit END

    public PacketGuiAddProductionRequest() {}

    public PacketGuiAddProductionRequest(int outputBlocks_index_producing) {
        this.outputBlocks_index_producing = outputBlocks_index_producing;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.outputBlocks_index_producing);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.outputBlocks_index_producing = buf.readInt();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiAddProductionRequest, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiAddProductionRequest message, MessageContext ctx) {
            if (ctx.side != Side.SERVER)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiAddProductionRequest message) {

            ContainerMachineFacadingbench openContainer = (ContainerMachineFacadingbench) GuiHandler.getOpenContainer();
            if (openContainer == null)
                return;

            openContainer.productionAdded(message.outputBlocks_index_producing);

        }
    }

}
