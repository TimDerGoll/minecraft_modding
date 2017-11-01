package de.timgoll.facading.network.facadingbench;

import de.timgoll.facading.client.gui.GuiFacadingbenchContainer;
import de.timgoll.facading.client.gui.GuiHandler;
import de.timgoll.facading.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGuiAddProduction implements IMessage {

    //data to transmit

    private int outputBlocks_amount;
    private int outputBlocks_index_producing;

    //data to transmit END

    public PacketGuiAddProduction() {}

    public PacketGuiAddProduction(int outputBlocks_amount, int outputBlocks_index_producing) {
        this.outputBlocks_amount = outputBlocks_amount;
        this.outputBlocks_index_producing = outputBlocks_index_producing;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.outputBlocks_amount);
        buf.writeInt(this.outputBlocks_index_producing);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {

            this.outputBlocks_amount = buf.readInt();
            this.outputBlocks_index_producing = buf.readInt();

        } catch (IndexOutOfBoundsException ioe) {
            Utils.getLogger().catching(ioe);
            return;
        }
    }


    public static class Handler implements IMessageHandler<PacketGuiAddProduction, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiAddProduction message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketGuiAddProduction message) {

            GuiFacadingbenchContainer openGui = (GuiFacadingbenchContainer) GuiHandler.getOpenGui();
            if (openGui == null)
                return;

            openGui.setProduction(message.outputBlocks_amount, message.outputBlocks_index_producing);

        }
    }

}
