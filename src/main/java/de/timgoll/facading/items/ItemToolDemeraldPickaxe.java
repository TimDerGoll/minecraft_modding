package de.timgoll.facading.items;

import de.timgoll.facading.Facading;
import de.timgoll.facading.init.ModRegistry;
import net.minecraft.item.ItemPickaxe;

public class ItemToolDemeraldPickaxe extends ItemPickaxe {

    public ItemToolDemeraldPickaxe(String name) {
        super(ModRegistry.TM_DEMERALD);

        this.setRegistryName(name);
        this.setUnlocalizedName(Facading.MODID + "." + name);
        this.setCreativeTab(ModRegistry.TAB);

        ModRegistry.ITEMS.add(this);
    }


}
