package com.mic.betterslimes.entity;

import MICDeps.util.handlers.ConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBetterSlime extends EntitySlime {

	public static float damageMultiplier = 1;
	public static int splitChance = ConfigHandler.splitChance;

	public double attackMod = 1;
	public double healthMod = 1;
	public double speedMod = 1;
	private int tick = 0;

	public EntityBetterSlime(World worldIn) {

		super(worldIn);
	}

	@Override
	protected EntityBetterSlime createInstance() {

		return new EntityBetterSlime(this.world);
	}

	@Override
	protected void dealDamage(EntityLivingBase entityIn)
	{
		int i = this.getSlimeSize();

		if (this.canEntityBeSeen(entityIn) && this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength()))
		{
			this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

			entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (i+1) * damageMultiplier);
		}
	}

	public void setAttackModifier(double mod) {
		this.attackMod = mod;
		setSlimeSize(getSlimeSize(), true);
	}

	public void setHealthModifier(double mod) {
		this.healthMod = mod;
		setSlimeSize(getSlimeSize(), true);
	}

	public void setSpeedModifier(double mod) {
		this.speedMod = mod;
		setSlimeSize(getSlimeSize(), true);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if (splitChance > 0) {
			if (tick > 200) {
				if (rand.nextInt(splitChance) > rand.nextInt(100)) {
					split();
				}
				tick = 0;
			}
			tick++;
		}
	}

	public void split() {
		EntityBetterSlime s = this;
		if (s.getSlimeSize() > 1) {
			EntityBetterSlime p = s.createInstance();
			p.setSlimeSize(1, true);
			p.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ, this.rand.nextFloat() * 360.0F, 0.0F);
			this.world.spawnEntity(p);
		}

	}

	@Override
	public void setSlimeSize(int size, boolean resetHealth) {
		super.setSlimeSize(size, resetHealth);

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) (size * size * attackMod));
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
				.setBaseValue((double) (0.2F + 0.1F * (float) size * speedMod));

		if (resetHealth) {
			this.setHealth(this.getMaxHealth());
		}

	}

	@Override
	protected boolean canDamagePlayer() {
		return true;
	}

}
