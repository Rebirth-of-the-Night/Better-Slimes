package com.mic.betterslimes.sounds;

import java.util.ArrayList;
import java.util.List;

import com.mic.betterslimes.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class ModSounds {

    public static final List<SoundEvent> MOD_SOUNDS = new ArrayList<>();

    public static final SoundEvent ENTITY_QUAZAR_HURT = registerSound("entity.quazar.hurt");
    public static final SoundEvent ENTITY_QUAZAR_DEATH = registerSound("entity.quazar.death");

    private static SoundEvent registerSound(String name) {
        SoundEvent s = new SoundEvent(new ResourceLocation(Reference.MODID, name));
        s.setRegistryName(new ResourceLocation(Reference.MODID, name));

        MOD_SOUNDS.add(s);

        return s;
    }
}