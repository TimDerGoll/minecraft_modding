package de.timgoll.facading;

import de.timgoll.facading.item.ModItems;
import de.timgoll.facading.proxy.CommonProxy;
import de.timgoll.facading.tab.CreativeTabFacading;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Facading.MODID, version = Facading.VERSION, name = Facading.NAME)
public class Facading {
    public static final String MODID = "facading";
    public static final String VERSION = "0.1";
    public static final String NAME = "Facading";

    @SidedProxy(clientSide = "de.timgoll.facading.proxy.ClientProxy", serverSide = "de.timgoll.facading.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Facading instance;

    public static CreativeTabFacading tabFacading;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        tabFacading = new CreativeTabFacading(CreativeTabs.getNextID(), "tab_facading");
        ModItems.preInit();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}