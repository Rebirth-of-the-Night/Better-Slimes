package com.mic.betterslimes.entity.slimes;

import javax.annotation.Nullable;

import com.mic.betterslimes.entity.EntityBetterSlime;
import com.mic.betterslimes.entity.ISpecialSlime;
import com.mic.betterslimes.util.LootTables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class IronSlime extends EntityBetterSlime implements ISpecialSlime{

	public IronSlime(World worldIn) {
		super(worldIn);
		this.setAttackModifier(2);
		this.setHealthModifier(4);
	}
	
	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
		super.knockBack(entityIn, strength * 0.1F, xRatio, zRatio);
	}

	@Override
	protected EntityBetterSlime createInstance() {
		return new IronSlime(this.world);
	}

	@Override
	public boolean getCanSpawnHere() {

		if (this.world.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(rand, world)) {
			return false;
		} else {
			if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {

				if (this.posY < 58 && this.posY > 2) {
					return true;
				}

			}

			return false;
		}
	}

	
//	@Override
//	protected EnumParticleTypes getParticleType() {
//		
//		return EnumParticleTypes.BLOCK_CRACK;
//	}
	
	@Override
	protected SoundEvent getSquishSound() {
		return SoundEvents.BLOCK_ANVIL_PLACE;
	}
	
	@Override
	protected int getAttackStrength() {
		return (int) (super.getAttackStrength() * attackMod);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	}

	@Nullable
    protected ResourceLocation getLootTable()
    {
        return this.getSlimeSize() == 1 ? LootTables.ironSlimeLT : LootTableList.EMPTY;
    }
}
