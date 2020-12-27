package MICDeps.util.handlers;

import MICDeps.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {
    public static SoundEvent ENTITY_QUAZAR_HURT, ENTITY_QUAZAR_DEATH;

    public static void registerSounds() {
//        ENTITY_QUAZAR_HURT = registerSound("entity.quazar.hurt");
//        ENTITY_QUAZAR_DEATH = registerSound("entity.quazar.death");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Reference.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}