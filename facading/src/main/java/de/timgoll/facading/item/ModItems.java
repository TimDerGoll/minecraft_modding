package de.timgoll.facading.item;

import de.timgoll.facading.Facading;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModItems extends Event {

    public static Item tutorialItem;

    public static void preInit() {

    }

    @SubscribeEvent
    public static void newItems(RegistryEvent.NewRegistry event) {
        System.out.println("[MY MOD] DEBUG 1 ....................................................................................................................");

        tutorialItem = new Item();
        tutorialItem.setRegistryName("tutorial_item");
        tutorialItem.setUnlocalizedName("tutorial_item");
        tutorialItem.setCreativeTab(Facading.tabFacading);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        System.out.println("[MY MOD] DEBUG 2 ....................................................................................................................");

        event.getRegistry().register(tutorialItem);
    }

    public static void registerRenders() {
        registerRender(tutorialItem);
    }

    public static void registerRender(Item item) {
        //System.out.println("Facading.MODID " + Facading.MODID);
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
        //        item, 0, new ModelResourceLocation(Facading.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory")
        //);

        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Facading.MODID + ":" + item.getUnlocalizedName().substring(5), "invetory"));
    }

}
