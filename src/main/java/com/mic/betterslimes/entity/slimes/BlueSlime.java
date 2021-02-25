package com.mic.betterslimes.entity.slimes;

import javax.annotation.Nullable;

import com.mic.betterslimes.BetterSlimes;
import com.mic.betterslimes.entity.EntityBetterSlime;

import com.mic.betterslimes.entity.ISpecialSlime;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class BlueSlime extends EntityBetterSlime implements ISpecialSlime {

	public BlueSlime(World worldIn) {
		super(worldIn);
	}

	@Override
	protected EntityBetterSlime createInstance() {

		return new BlueSlime(this.world);
	}

	@Override
	public boolean getCanSpawnHere() {

		if (this.world.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(rand, world)) {
			return false;
		} else {
			if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {

				return true;

			}

			return false;
		}
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return this.getSlimeSize() == 1 ? BetterSlimes.blueSlimeLT : LootTableList.EMPTY;
	}

}
