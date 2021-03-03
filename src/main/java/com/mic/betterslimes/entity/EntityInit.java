package com.mic.betterslimes.entity;

import MICDeps.Reference;
import com.mic.betterslimes.BetterSlimes;
import com.mic.betterslimes.entity.slimes.*;

import MICDeps.util.handlers.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityInit {
    public EntityInit() {
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int ID, int range, int colorOne, int colorTwo) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), 
                entity, name, ID, BetterSlimes.instance, range, 1, true, colorOne, colorTwo);
    }

    public static void registerEntity() {
        int view = 60;

        registerEntity("blue_slime", BlueSlime.class, 111, view, 0x1219CF, 0x0000FF);
        registerEntity("red_slime", RedSlime.class, 112, view, 0xB8363A, 0xFF0000);
        registerEntity("yellow_slime", YellowSlime.class, 113, view, 0xCCBE00, 0xFFFF00);
        registerEntity("black_slime", BlackSlime.class, 114, view, 0x363535, 0x000000);
        registerEntity("purple_slime", PurpleSlime.class, 115, view, 0x9A359D, 0x950097);
        registerEntity("ice_slime", IceSlime.class, 116, view, 0x4184FF, 0x41B4FF);
        registerEntity("jungle_slime", JungleSlime.class, 117, view, 0x007700, 0x00DA00);
        registerEntity("sand_slime", SandSlime.class, 118, view, 0xC6BEB5, 0xE1DEB5);
        registerEntity("spectral_slime", SpectralSlime.class, 119, view, 0x9A359D, 0x000000);
        registerEntity("quazar", Quazar.class, 120, view, 0x1219CF, 0xFFFF00);
        registerEntity("iron_slime", IronSlime.class, 121, view, 0x6D7070, 0xADAF95);
        registerEntity("gold_slime", GoldSlime.class, 122, view, 0xDBCC00, 0xFFFF00);
        registerEntity("knight_slime", KnightSlime.class, 123, view, 0x6D7070, 0x0000FF);
        registerEntity("haunted_slime", HauntedSlime.class, 124, view, 0xB8363A, 0x000000);

        addToBiomes();
    }

    public static void addToBiomes() {
        System.out.println("Adding spawns to biomes");
        // Jungle slime
        if (ConfigHandler.jungleSlime > 0) {
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE);
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE_EDGE);
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE_HILLS);
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.MUTATED_JUNGLE);
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.MUTATED_JUNGLE_EDGE);
        }

        // Spectral slime, haunted slime
        if (ConfigHandler.skySlime > 0) {
            EntityRegistry.addSpawn(HauntedSlime.class, ConfigHandler.skySlime, 0, 1, EnumCreatureType.MONSTER, Biomes.HELL);
            EntityRegistry.addSpawn(SpectralSlime.class, ConfigHandler.skySlime, 0, 1, EnumCreatureType.MONSTER, Biomes.SKY);
        }

        // Ice slime
        if (ConfigHandler.iceSlime > 0)
            for (Biome b : BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY))
                if (!(b.equals(Biomes.HELL) || b.equals(Biomes.SKY)))
                    EntityRegistry.addSpawn(IceSlime.class, ConfigHandler.iceSlime, 0, 3, EnumCreatureType.MONSTER, b);
        
        // Sand slime
        if (ConfigHandler.sandSlime > 0)
            for (Biome b : BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY))
                if (!(b.equals(Biomes.HELL) || b.equals(Biomes.SKY)) && ConfigHandler.sandSlime > 0)
                    EntityRegistry.addSpawn(SandSlime.class, ConfigHandler.sandSlime, 0, 6, EnumCreatureType.MONSTER, b);
        
        // Generic slimes
        for (Biome b : ForgeRegistries.BIOMES.getValuesCollection()) {
            if (!(b.equals(Biomes.HELL) || b.equals(Biomes.SKY))) {
//                if (ConfigHandler.blueSlime > 0)
                    EntityRegistry.addSpawn(BlueSlime.class, ConfigHandler.blueSlime, 0, 6, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.redSlime > 0)
                    EntityRegistry.addSpawn(RedSlime.class, ConfigHandler.redSlime, 0, 5, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.yellowSlime > 0)
                    EntityRegistry.addSpawn(YellowSlime.class, ConfigHandler.yellowSlime, 0, 4, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.purpleSlime > 0)
                    EntityRegistry.addSpawn(PurpleSlime.class, ConfigHandler.purpleSlime, 0, 4, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.blackSlime > 0)
                    EntityRegistry.addSpawn(BlackSlime.class, ConfigHandler.blackSlime, 0, 6, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.ironSlime > 0)
                    EntityRegistry.addSpawn(IronSlime.class, ConfigHandler.ironSlime, 0, 2, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.goldSlime > 0)
                    EntityRegistry.addSpawn(GoldSlime.class, ConfigHandler.goldSlime, 0, 3, EnumCreatureType.MONSTER, b);
//                if (ConfigHandler.knightSlime > 0)
                    EntityRegistry.addSpawn(KnightSlime.class, ConfigHandler.knightSlime, 0, 7, EnumCreatureType.MONSTER, b);
            }
        }
    }
}
