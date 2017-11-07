package de.timgoll.facadingIndustry.network;

import de.timgoll.facadingIndustry.network.facadingbench.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        // Server packets
        INSTANCE.registerMessage(PacketGuiOpenedRequest.Handler.class, PacketGuiOpenedRequest.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketGuiCancelProductionRequest.Handler.class, PacketGuiCancelProductionRequest.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketGuiAddProductionRequest.Handler.class, PacketGuiAddProductionRequest.class, nextID(), Side.SERVER);

        // Client packets
        INSTANCE.registerMessage(PacketGuiOpened.Handler.class, PacketGuiOpened.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketGuiCancelProduction.Handler.class, PacketGuiCancelProduction.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketGuiAddProduction.Handler.class, PacketGuiAddProduction.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketGuiStartedProduction.Handler.class, PacketGuiStartedProduction.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketGuiStartedDisassembly.Handler.class, PacketGuiStartedDisassembly.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PackedGuiFinishedProduction.Handler.class, PackedGuiFinishedProduction.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketGuiIsPowered.Handler.class, PacketGuiIsPowered.class, nextID(), Side.CLIENT);
    }

}
