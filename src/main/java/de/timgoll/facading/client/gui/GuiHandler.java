package de.timgoll.facading.client.gui;

import de.timgoll.facading.container.ContainerFacadingbench;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_FACADING_CONTAINER_ID = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case GUI_FACADING_CONTAINER_ID:
                return new ContainerFacadingbench(player.inventory, (TileBlockFacadingbench) te);
            default: return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case GUI_FACADING_CONTAINER_ID:
                return new GuiFacadingbenchContainer(player.inventory, (TileBlockFacadingbench) te);
            default: return null;
        }
    }

}
