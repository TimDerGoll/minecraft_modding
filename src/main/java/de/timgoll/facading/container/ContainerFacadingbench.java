package de.timgoll.facading.container;

import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerFacadingbench extends Container {

    public ContainerFacadingbench(InventoryPlayer inventoryPlayer, TileBlockFacadingbench tileBlockFacadingbench) {

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
