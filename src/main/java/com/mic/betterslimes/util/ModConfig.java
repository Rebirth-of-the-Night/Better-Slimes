package com.mic.betterslimes.util;

import java.io.File;

import com.mic.betterslimes.entity.slimes.Quazar;
import net.minecraftforge.common.config.Configuration;

import static com.mic.betterslimes.entity.EntityBetterSlime.damageMultiplier;

public class ModConfig {
	public static Configuration config;
    public static boolean startupMessage = true;
    public static boolean convertSlimes = true;
    public static boolean convertIgnoreChance = true;
	
	public static int blueSlime = 14;
	public static int redSlime = 7;
	public static int yellowSlime = 4;
	public static int purpleSlime = 2;
	public static int blackSlime = 80;
	public static int iceSlime = 8;
	public static int jungleSlime = 20;
	public static int sandSlime = 20;
    public static int spectralSlime = 12;
    public static int hauntedSlime = 12;
	public static int kingChance = 5;
	public static int ironSlime = 40;
	public static int goldSlime = 20;
    public static int knightSlime = 6;
    
	public static int splitChance = 50;

	public static final int MAX = Short.MAX_VALUE;

	public static void init(File file) {

		config = new Configuration(file);
		String category;

		// Drop chances
		category = "Better Slimes Config";

		startupMessage = config.getBoolean("Start-Up Message?", category, startupMessage, "Give a start-up thank you?");
		kingChance = config.getInt("King Slime Spawn Chance", category, kingChance, 0, 100, "0 for never and 100 for every night.");
		splitChance = config.getInt("Slime Splitting Chance", category, splitChance, 0, 100, "0 for never and 100 for always.");
        damageMultiplier = config.getFloat("Damage Multiplier", category, damageMultiplier, 0, MAX, "Custom slime damage multiplier");
        convertSlimes = config.getBoolean("Convert Slimes?", category, convertSlimes, "Convert slimes that spawn to their biome-specific type, if applicable?");
        convertIgnoreChance = config.getBoolean("Ignore Spawn Chance?", category, convertIgnoreChance, "If convert slimes is enabled, ignore the spawn chance being 0 when spawning a specific slime?");

		category = "Slime Spawn Chances";

		blueSlime = config.getInt("Blue Slime Spawn Chance", category, blueSlime, 0, 100, "0 for never and 100 for always.");
		redSlime = config.getInt("Red Slime Spawn Chance", category, redSlime, 0, 100, "0 for never and 100 for always.");
		yellowSlime = config.getInt("Yellow Slime Spawn Chance", category, yellowSlime, 0, 100, "0 for never and 100 for always.");
		purpleSlime = config.getInt("Purple Slime Spawn Chance", category, purpleSlime, 0, 100, "0 for never and 100 for always.");
		blackSlime = config.getInt("Black Slime Spawn Chance", category, blackSlime, 0, 100, "0 for never and 100 for always.");
		iceSlime = config.getInt("Ice Slime Spawn Chance", category, iceSlime, 0, 100, "0 for never and 100 for always.");
		jungleSlime = config.getInt("Jungle Slime Spawn Chance", category, jungleSlime, 0, 100, "0 for never and 100 for always.");
		sandSlime = config.getInt("Sand Slime Spawn Chance", category, sandSlime, 0, 100, "0 for never and 100 for always.");
		spectralSlime = config.getInt("Spectral Slime Spawn Chance", category, spectralSlime, 0, 100, "0 for never and 100 for always.");
		hauntedSlime = config.getInt("Haunted Slime Spawn Chance", category, hauntedSlime, 0, 100, "0 for never and 100 for always.");
		ironSlime = config.getInt("Iron Slime Spawn Chance", category, ironSlime, 0, 100, "0 for never and 100 for always.");
		goldSlime = config.getInt("Gold Slime Spawn Chance", category, goldSlime, 0, 100, "0 for never and 100 for always.");
		knightSlime = config.getInt("Knight Slime Spawn Chance", category, knightSlime, 0, 100, "0 for never and 100 for always.");


		Quazar.initConfig(config);

		config.save();

	}
}
