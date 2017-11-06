package de.timgoll.facading.sounds;

import de.timgoll.facading.Facading;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundHandler {

    private SoundEvent soundEvent;

    public SoundHandler(String name) {
        ResourceLocation location = new ResourceLocation(Facading.MODID, name);
        soundEvent = new SoundEvent(location);
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }
}
