package de.timgoll.facadingIndustry.sounds;

import de.timgoll.facadingIndustry.FacadingIndustry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundHandler {

    private SoundEvent soundEvent;

    public SoundHandler(String name) {
        ResourceLocation location = new ResourceLocation(FacadingIndustry.MODID, name);
        soundEvent = new SoundEvent(location);
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }
}
