package com.mic.betterslimes.entity;

import com.mic.betterslimes.util.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mic.betterslimes.entity.slimes.*;
import com.mic.betterslimes.handlers.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModEntities {
    public static final List<EntityEntry> MOD_ENTITIES = new ArrayList<>();

    private static int idCounter = 111;

    @SuppressWarnings("unused")
    private static void registerEntity(String name, Class<? extends Entity> entity, int range, int colorOne, int colorTwo) {
        registerEntity(name, entity, range, colorOne, colorTwo, 0, 0, 0, null, (Iterable<Biome>) null);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int range, int colorOne, int colorTwo,
            int spawnChance, int groupMin, int groupMax, EnumCreatureType type, Biome... biomes) {
        registerEntity(name, entity, range, colorOne, colorTwo, spawnChance, groupMin, groupMax, type, Arrays.asList(biomes));
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int range, int colorOne, int colorTwo, 
            int spawnChance, int groupMin, int groupMax, EnumCreatureType type, Iterable<Biome> biomes) {
        EntityEntryBuilder<? extends Entity> builder = EntityEntryBuilder.create()
                                .name(name)
                                .entity(entity)
                                .id(new ResourceLocation(Reference.MODID + ":" + name), idCounter)
                                .tracker(range, 1, true)
                                .egg(colorOne, colorTwo);
        
        if (spawnChance > 0)
            builder = builder.spawn(type, spawnChance, groupMin, groupMax, biomes);
        
        MOD_ENTITIES.add(builder.build());
        idCounter++;
    }

    public static void registerEntities() {
        int view = 60;

        Set<Biome> genericBiomes = excludeHellAndSky(ForgeRegistries.BIOMES.getValuesCollection());

        registerEntity("blue_slime", BlueSlime.class, view, 0x1219CF, 0x0000FF,
                ConfigHandler.blueSlime, 0, 6, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("red_slime", RedSlime.class, view, 0xB8363A, 0xFF0000,
                ConfigHandler.redSlime, 0, 5, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("yellow_slime", YellowSlime.class, view, 0xCCBE00, 0xFFFF00,
                ConfigHandler.yellowSlime, 0, 4, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("black_slime", BlackSlime.class, view, 0x363535, 0x000000,
                ConfigHandler.blackSlime, 0, 6, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("purple_slime", PurpleSlime.class, view, 0x9A359D, 0x950097,
                ConfigHandler.purpleSlime, 0, 4, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("ice_slime", IceSlime.class, view, 0x4184FF, 0x41B4FF,
                ConfigHandler.iceSlime, 0, 6, EnumCreatureType.MONSTER, excludeHellAndSky(BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY)));
        registerEntity("jungle_slime", JungleSlime.class, view, 0x007700, 0x00DA00,
                ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, excludeHellAndSky(BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE)));
        registerEntity("sand_slime", SandSlime.class, view, 0xC6BEB5, 0xE1DEB5,
                ConfigHandler.sandSlime, 0, 6, EnumCreatureType.MONSTER, excludeHellAndSky(BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY)));
        registerEntity("spectral_slime", SpectralSlime.class, view, 0x9A359D, 0x000000,
                ConfigHandler.spectralSlime, 0, 1, EnumCreatureType.MONSTER, Biomes.SKY);
        registerEntity("quazar", Quazar.class, view, 0x1219CF, 0xFFFF00,
                ConfigHandler.kingChance, 0, 1, EnumCreatureType.MONSTER, excludeHellAndSky(BiomeDictionary.getBiomes(BiomeDictionary.Type.SPOOKY)));
        registerEntity("iron_slime", IronSlime.class, view, 0x6D7070, 0xADAF95,
                ConfigHandler.ironSlime, 0, 2, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("gold_slime", GoldSlime.class, view, 0xDBCC00, 0xFFFF00,
                ConfigHandler.goldSlime, 0, 3, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("knight_slime", KnightSlime.class, view, 0x6D7070, 0x0000FF,
                ConfigHandler.knightSlime, 0, 7, EnumCreatureType.MONSTER, genericBiomes);
        registerEntity("haunted_slime", HauntedSlime.class, view, 0xB8363A, 0x000000,
                ConfigHandler.hauntedSlime, 0, 1, EnumCreatureType.MONSTER, Biomes.HELL);
    }

    private static Set<Biome> excludeHellAndSky(Collection<Biome> c) {
        Set<Biome> s = new HashSet<>(c);
        s.remove(Biomes.HELL);
        s.remove(Biomes.SKY);
        return s;
    }
}
