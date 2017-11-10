package de.timgoll.facading.client.gui;

import de.timgoll.facading.container.ContainerMachineFacadingbench;
import de.timgoll.facading.container.ContainerMachineNailMaker;
import de.timgoll.facading.container.ContainerMachinePress;
import de.timgoll.facading.container.ContainerMachineSawmill;
import de.timgoll.facading.titleentities.TileBlockMachineFacadingbench;
import de.timgoll.facading.titleentities.TileBlockMachineNailMaker;
import de.timgoll.facading.titleentities.TileBlockMachinePress;
import de.timgoll.facading.titleentities.TileBlockMachineSawmill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_FACADINGBENCH_CONTAINER_ID = 0;
    public static final int GUI_PRESS_CONTAINER_ID = 1;
    public static final int GUI_NAILMAKER_CONTAINER_ID = 2;
    public static final int GUI_SAWMILL_CONTAINER_ID = 3;

    private static Object OPENGUI;
    private static Object OPENCONTAINER;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case GUI_FACADINGBENCH_CONTAINER_ID:
                OPENCONTAINER = new ContainerMachineFacadingbench(player.inventory, (TileBlockMachineFacadingbench) te); break;
            case GUI_PRESS_CONTAINER_ID:
                OPENCONTAINER = new ContainerMachinePress(player.inventory, (TileBlockMachinePress) te); break;
            case GUI_NAILMAKER_CONTAINER_ID:
                OPENCONTAINER = new ContainerMachineNailMaker(player.inventory, (TileBlockMachineNailMaker) te); break;
            case GUI_SAWMILL_CONTAINER_ID:
                OPENCONTAINER = new ContainerMachineSawmill(player.inventory, (TileBlockMachineSawmill) te); break;
            default: OPENCONTAINER = null;
        }

        return OPENCONTAINER;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case GUI_FACADINGBENCH_CONTAINER_ID:
                OPENGUI = new GuiMachineFacadingbench(player.inventory, (TileBlockMachineFacadingbench) te); break;
            case GUI_PRESS_CONTAINER_ID:
                OPENGUI = new GuiMachinePress(player.inventory, (TileBlockMachinePress) te); break;
            case GUI_NAILMAKER_CONTAINER_ID:
                OPENGUI = new GuiMachineNailMaker(player.inventory, (TileBlockMachineNailMaker) te); break;
            case GUI_SAWMILL_CONTAINER_ID:
                OPENGUI = new GuiMachineSawmill(player.inventory, (TileBlockMachineSawmill) te); break;
            default: OPENGUI = null;
        }

        return OPENGUI;
    }

    public static Object getOpenGui() {
        return OPENGUI;
    }

    public static Object getOpenContainer() {
        return OPENCONTAINER;
    }
}
