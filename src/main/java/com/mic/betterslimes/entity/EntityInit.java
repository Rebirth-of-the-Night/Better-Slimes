package com.mic.betterslimes.entity;

import MICDeps.Reference;
import com.mic.betterslimes.BetterSlimes;
import com.mic.betterslimes.entity.slimes.*;

import MICDeps.ModBase;
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
    public static ModBase mod = BetterSlimes.instance;

    public EntityInit() {
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int ID, int range, int colorOne,
                                       int colorTwo) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, ID, mod.instance,
                range, 1, true, colorOne, colorTwo);
    }

    public static void registerEntity() {
        int view = 60;

        registerEntity("blue_slime", BlueSlime.class, 111, view, 1186255, 255);
        registerEntity("red_slime", RedSlime.class, 112, view, 12072506, 16711680);
        registerEntity("yellow_slime", YellowSlime.class, 113, view, 13417984, 16776960);
        registerEntity("black_slime", BlackSlime.class, 114, view, 3552565, 000000);
        registerEntity("purple_slime", PurpleSlime.class, 115, view, 10106269, 9765015);
        registerEntity("ice_slime", IceSlime.class, 116, view, 4293887, 4306175);
        registerEntity("jungle_slime", JungleSlime.class, 117, view, 30464, 55808);
        registerEntity("sand_slime", SandSlime.class, 118, view, 13024949, 14802613);
        registerEntity("spectral_slime", SpectralSlime.class, 119, view, 10106269, 000000);
        registerEntity("quazar", Quazar.class, 120, view, 1186255, 16776960);
        registerEntity("iron_slime", IronSlime.class, 121, view, 7172208, 11382677);
        registerEntity("gold_slime", GoldSlime.class, 122, view, 14404608, 16776960);
        registerEntity("knight_slime", KnightSlime.class, 123, view, 7172208, 255);
        registerEntity("haunted_slime", HauntedSlime.class, 124, view, 12072506, 000000);

        addToBiomes();
    }

    public static void addToBiomes() {
        System.out.println("Adding spawns to biomes");
        if (ConfigHandler.jungleSlime > 0)
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE);
        if (ConfigHandler.jungleSlime > 0)
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE_EDGE);
        if (ConfigHandler.jungleSlime > 0)
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE_HILLS);
        if (ConfigHandler.jungleSlime > 0)
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.MUTATED_JUNGLE);
        if (ConfigHandler.jungleSlime > 0)
            EntityRegistry.addSpawn(JungleSlime.class, ConfigHandler.jungleSlime, 0, 6, EnumCreatureType.MONSTER, Biomes.MUTATED_JUNGLE_EDGE);

        if (ConfigHandler.skySlime > 0)
            EntityRegistry.addSpawn(HauntedSlime.class, ConfigHandler.skySlime, 0, 1, EnumCreatureType.MONSTER, Biomes.HELL);

        if (ConfigHandler.skySlime > 0)
            EntityRegistry.addSpawn(SpectralSlime.class, ConfigHandler.skySlime, 0, 1, EnumCreatureType.MONSTER, Biomes.SKY);

        for (Biome b : BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY)) {
            // System.out.println(b.getBiomeName());
            if (!(b.equals(Biomes.HELL) || b.equals(Biomes.SKY)) && ConfigHandler.iceSlime > 0)
                EntityRegistry.addSpawn(IceSlime.class, ConfigHandler.iceSlime, 0, 3, EnumCreatureType.MONSTER, b);
        }
        for (Biome b : BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY)) {
            // System.out.println(b.getBiomeName());
            if (!(b.equals(Biomes.HELL) || b.equals(Biomes.SKY)) && ConfigHandler.sandSlime > 0)
                EntityRegistry.addSpawn(SandSlime.class, ConfigHandler.sandSlime, 0, 6, EnumCreatureType.MONSTER, b);
        }
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
