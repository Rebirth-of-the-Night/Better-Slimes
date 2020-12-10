package MICDeps.util.handlers;

import MICDeps.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {
    public static SoundEvent ENTITY_KING_SLIME_HURT, ENTITY_KING_SLIME_DEATH;

    public static void registerSounds() {
//        ENTITY_KING_SLIME_HURT = registerSound("entity.king_slime.hurt");
//        ENTITY_KING_SLIME_DEATH = registerSound("entity.king_slime.death");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Reference.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
