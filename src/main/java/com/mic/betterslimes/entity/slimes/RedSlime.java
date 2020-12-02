package com.mic.betterslimes.entity.slimes;

import javax.annotation.Nullable;

import com.mic.betterslimes.BetterSlimes;
import com.mic.betterslimes.entity.EntityBetterSlime;

import com.mic.betterslimes.entity.ISpecialSlime;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;

public class RedSlime extends EntityBetterSlime implements ISpecialSlime {

	public RedSlime(World worldIn) {
		super(worldIn);
		this.setAttackModifier(1.5);
		this.setHealthModifier(1.5);
	}
	
	@Override
	protected EntityBetterSlime createInstance()
    {
		
        return new RedSlime(this.world);
    }
	
	@Override
	public boolean getCanSpawnHere()
    {
        if (this.world.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(rand, world))
        {
            return false;
        }
        else
        {
            if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL)
            {
            	
            		return true;
            	

            }

            return false;
        }
    }
	@Nullable
    protected ResourceLocation getLootTable()
    {
        return this.getSlimeSize() == 1 ? BetterSlimes.redSlimeLT : LootTableList.EMPTY;
    }
	
	@Override
	protected int getAttackStrength() {
		return (int) (super.getAttackStrength() * attackMod);
	}
	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	}
	

}
