package com.mic.betterslimes.entity.slimes;

import javax.annotation.Nullable;

import com.mic.betterslimes.entity.EntityBetterSlime;
import com.mic.betterslimes.entity.ISpecialSlime;
import com.mic.betterslimes.util.LootTables;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class JungleSlime extends EntityBetterSlime implements ISpecialSlime{

	public JungleSlime(World worldIn) {
		super(worldIn);
		this.setAttackModifier(2.5);
		this.setHealthModifier(2.5);
	}
	
	@Override
	protected EntityBetterSlime createInstance()
    {
        return new JungleSlime(this.world);
    }
	
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
	
	@Override
	protected int getAttackStrength() {
		return (int) (super.getAttackStrength() * attackMod);
	}
	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	}
	
	@Nullable
    protected ResourceLocation getLootTable()
    {
        return this.getSlimeSize() == 1 ? LootTables.jungleSlimeLT : LootTableList.EMPTY;
    }

}
