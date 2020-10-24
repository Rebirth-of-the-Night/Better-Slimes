package com.mic.betterslimes.entity.slimes;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.mic.betterslimes.BetterSlimes;
import com.mic.betterslimes.entity.EntityBetterSlime;
import com.mic.betterslimes.entity.ISpecialSlime;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;

import static MICDeps.util.handlers.ConfigHandler.config;
import static com.mic.betterslimes.BetterSlimes.MODID;

public class KingSlime extends EntityBetterSlime implements ISpecialSlime {

    public static final String MODID = BetterSlimes.MODID;

    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
            BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS));
    private static final DataParameter<Integer> SPAWN_TIME = EntityDataManager.<Integer>createKey(KingSlime.class,
            DataSerializers.VARINT);
    public static final String NAME = "KingSlime";
    public static final int MAX = Short.MAX_VALUE;

    protected boolean explode = false;
    public static float explodeDamage = 18.0f;
    public static int explodeRange = 32;

    Integer targetLastPosX = null;
    Integer targetLastPosZ = null;

    // Time in ticks between leaps
    public static int leapCooldown;
    // Time in ticks the boss takaes to charge up his leap attack
    public static int leapWarning;

    public static float leapVelocityMultiplierY;
    public static float leapVelocityMultiplierXZ;
    public static float movementSpeedMultiplier;
    public static int size = 7;

    private int timeSinceIgnited = 0;
    private int fuseTime = 30;
    private int combatTimer = 2;

    public int bossTimer = 160;
    private double movementSpeedAttribute;
    public static boolean configLoaded = false;

    public static boolean spawnMinions;

    public static String splitSlimeString;

    protected Class<? extends Entity> SplitSlime;

    public static void initConfig() {
        config.addCustomCategoryComment(NAME, "Configuration options for the King Slime boss");
        leapCooldown = config.getInt(NAME, "leapCooldown", 160, 0, MAX, "Cooldown between leap attacks in ticks");
        leapWarning = config.getInt(NAME, "leapWarning", 40, 0, MAX, "Length of the animation the boss does before leap attacks in ticks. \nMust be longer than leapCooldown");

        leapVelocityMultiplierY = config.getFloat(NAME, "leapVelocityMultiplierY", 1.0F, 0, MAX, "Vertical speed all entities affected by the leap attack get");
        leapVelocityMultiplierXZ = config.getFloat(NAME, "leapVelocityMultiplierXZ", 1.0F, 0, MAX, "Horizontal speed all entities affected by the leap attack get");

        explodeDamage = config.getFloat(NAME, "explodeDamage", 18.0F, 0, MAX, "Damage the leap attack deals");
        explodeRange = config.getInt(NAME, "explodeDamage", 32, 0, MAX, "Damage the leap attack deals");
        movementSpeedMultiplier = config.getFloat(NAME, "movementSpeedMultiplier", 1.0F, 0, MAX, "Amount by which the movement speed of " + NAME + " is multiplied");

        size = config.getInt(NAME, "movementSpeedMultiplier", 7, 0, MAX, "Amount by which the movement speed of " + NAME + "is multiplied");

        spawnMinions = config.getBoolean(NAME, "spawnMinions", false, "Ability of the boss to summon little slaves to aid him in battle");

        splitSlimeString = config.getString(NAME, "slimeChildren",  MODID + ":blue_slime", "The type of slime the boss will split into on death\n Must be a BetterSlimes slime");

        configLoaded = true;
    }

    public KingSlime(World worldIn) {
        super(worldIn);
        this.setAttackModifier(1);
        this.setHealthModifier(26);
        this.setSlimeSize(size, true);

        this.timeSinceIgnited = 0;
        this.fuseTime = 30;
        this.dataManager.register(STATE, Integer.valueOf(-1));
        this.setCreeperState(-1);

        this.movementSpeedAttribute = 0.1 * movementSpeedMultiplier;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movementSpeedAttribute);

        // Needed for event handling
        MinecraftForge.EVENT_BUS.register(this);
        SplitSlime = EntityList.getClassFromName(splitSlimeString);
    }

    // Needed for the creeper state
    private static final DataParameter<Integer> STATE = EntityDataManager.<Integer>createKey(KingSlime.class, DataSerializers.VARINT);

    public int getCreeperState() {
        return ((Integer) this.dataManager.get(STATE)).intValue();
    }

    public void setCreeperState(int state) {
        this.dataManager.set(STATE, Integer.valueOf(state));
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(SPAWN_TIME, Integer.valueOf(0));
        super.entityInit();
    }

    public int getSpawnTime() {
        return ((Integer) this.dataManager.get(SPAWN_TIME)).intValue();
    }

    public void setSpawnTime(int time) {
        this.dataManager.set(SPAWN_TIME, Integer.valueOf(time));
    }

    @Override
    protected void updateAITasks() {
        if (this.getSpawnTime() > 0) {
            int j1 = this.getSpawnTime() - 1;

            if (spawnMinions && j1 <= 0) {
                this.playSound(this.getSquishSound(), (float) (this.getSoundVolume() * 1.2), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
                for (int x = 0; x < 10; x++)
                    world.spawnParticle(EnumParticleTypes.SLIME, this.posX, this.getEntityBoundingBox().minY, this.posY, 0.0D, 0.0D, 0.0D);
//				this.world.spawnParticle(EnumParticleTypes.SLIME, this.posX, this.posY, this.posZ, 0, 0, 0);
                KnightSlime b;
                for (int x = 0; x < 4; x++) {
                    b = new KnightSlime(this.world);
                    b.setSlimeSize(2, true);
                    b.setLocationAndAngles(this.posX + rand.nextInt(10) - 5, this.posY + rand.nextInt(1) + 1,
                            this.posZ + rand.nextInt(10) - 5, this.rotationYaw, this.rotationPitch);
                    this.world.spawnEntity(b);
                }
            }

            this.setSpawnTime(j1);
        } else {
            this.setSpawnTime(240);
        }
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);

    }

    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (configLoaded) {
            compound.setInteger("Spawn", this.getSpawnTime());
            compound.setInteger("leapCooldown", leapCooldown);
            compound.setInteger("leapWarning", leapWarning);
            compound.setFloat("leapVelocityMultiplierY", leapVelocityMultiplierY);
            compound.setFloat("leapVelocityMultiplierXZ", leapVelocityMultiplierXZ);
            compound.setFloat("explodeDamage", explodeDamage);
            compound.setInteger("explodeRange", explodeRange);
            compound.setBoolean("spawnMinions", spawnMinions);
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (this.hasCustomName())
            this.bossInfo.setName(this.getDisplayName());
        
        if (compound.hasKey("Spawn"))
            this.setSpawnTime(compound.getInteger("Spawn"));
        if (configLoaded) {
            if (compound.hasKey("leapCooldown"))
                leapCooldown = compound.getInteger("leapCooldown");
            if (compound.hasKey("leapWarning"))
                leapWarning = compound.getInteger("leapWarning");
            if (compound.hasKey("leapVelocityMultiplierY"))
                leapVelocityMultiplierY = compound.getFloat("leapVelocityMultiplierY");
            if (compound.hasKey("leapVelocityMultiplierXZ"))
                leapVelocityMultiplierXZ = compound.getFloat("leapVelocityMultiplierXZ");
            if (compound.hasKey("explodeDamage"))
                explodeDamage = compound.getFloat("explodeDamage");
            if (compound.hasKey("explodeRange"))
                explodeRange = compound.getInteger("explodeRange");
            if (compound.hasKey("spawnMinions"))
                spawnMinions = compound.getBoolean("spawnMinions");
        }
    }

    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    protected EntityBetterSlime createInstance() {
        if (EntityBetterSlime.class.isAssignableFrom(SplitSlime)) {
            return (EntityBetterSlime)ForgeRegistries.ENTITIES.getValue(new ResourceLocation(splitSlimeString)).newInstance(this.world);
        } else {
            return new BlueSlime(this.world);
        }
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
    protected ResourceLocation getLootTable() {
        return BetterSlimes.kingSlimeLT;
    }

    protected void leap(EntityLivingBase leapTarget) {
        double d0;
        double d1;

        if (this.targetLastPosX != null && this.targetLastPosZ != null) {
            d0 = this.targetLastPosX - this.posX;
            d1 = this.targetLastPosZ - this.posZ;
        } else // fallback position, in case of null
        {
            d0 = 0; //leapTarget.posX - this.posX;
            d1 = 0; //leapTarget.posZ - this.posZ;
        }
            this.playSound(SoundEvents.BLOCK_CLOTH_PLACE, 2.0F, 0.3F);
        this.playSound(SoundEvents.BLOCK_SAND_FALL, 2.0F, 0.8F);
        this.setPositionAndUpdate(this.posX, this.posY + 2, this.posZ);
        if (!this.world.isRemote) this.addVelocity(d0 / 6, 2, d1 / 6);
    }

    private void explode() {
        if (!this.explode) {
            return;
        }
        this.explode = false;
        this.targetLastPosX = null;
        this.targetLastPosZ = null;

        List<EntityLivingBase> e = this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.getPosition()).grow(explodeRange, 32, explodeRange), new Predicate<EntityLivingBase>() {
            public boolean apply(@Nullable EntityLivingBase entity) {
                return true;
            }
        });

        for (EntityLivingBase entity : e) {
            double dist = this.getDistanceSq(entity) + 1;

            if (entity != this && dist < 512) {
                {
                    entity.setPositionAndUpdate(entity.posX, entity.posY + 1.5, entity.posZ);
                    entity.addVelocity((0.8 / (entity.posX - this.posX)) * leapVelocityMultiplierXZ, MathHelper.clamp(32 / (dist) * leapVelocityMultiplierY, 1, 16), 0.8 / (entity.posZ - this.posZ) * leapVelocityMultiplierXZ);
                    entity.velocityChanged = true;
                    entity.attackEntityFrom(DamageSource.GENERIC, (float) ((this.explodeDamage / (dist + 1))));
                    entity.setLastAttackedEntity(this);
                    entity.setRevengeTarget(this);
                }
            }
        }
    }

    protected void bossAbility(EntityLivingBase leapTarget) {
        double dist = this.getDistanceSq(leapTarget);

        if (dist < 40) {
            this.getNavigator().clearPath();
        }

        // decrement the boss timer
        if (this.bossTimer > 0) {
            this.bossTimer--;
        }

        // when the boss timer equals 40, capture that entity's last position
        if (this.bossTimer == leapWarning) {
            if (dist < 516) {
                this.targetLastPosX = (int) leapTarget.posX;
                this.targetLastPosZ = (int) leapTarget.posZ;
//                this.playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 2.0F, 1.2F);
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 2.0F, 0.8F);
                this.setCreeperState(1);
            } else if (bossTimer < leapCooldown) {
                // If the target is 24 blocks away, reset the cooldown
                bossTimer++;
            }
        }
        // when the boss timer below 40 and greater than 0, stop
        // moving capture and look at that entity's last position
        if (this.bossTimer <= leapWarning && this.bossTimer > 0) {
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movementSpeedAttribute);
        }

        // if the boss ability is ready, and boss is on the ground
        if (this.bossTimer < 1) //&& !this.isClearWebsReady )
        {
            this.leap(leapTarget);
            this.bossTimer = leapCooldown; //this.getRNG().nextInt(50);

            // Wait 2 ticks so the boss doesn't explode immediately after leaping
            this.combatTimer = 2;
            this.timeSinceIgnited = 0;
            this.fuseTime = 30;
            this.setCreeperState(-1);
            this.explode = true;
        }
    }

    public void onUpdate() {
        if (this.isEntityAlive()) {
            if (this.bossTimer <= leapWarning) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.7F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                //this.explode();
            }
        }

        super.onUpdate();
    }

    public void onLivingUpdate() // LIVING UPDATE ***
    {
        super.onLivingUpdate();

        if (this.world.isRemote) {
            return;
        }

        // if the boss has landed, explode
        if (this.onGround) // in the air
        {
            if (this.combatTimer < 1) {
                if (this.explode) {
                    explode();
                }
                this.combatTimer = 2;
            } else {
                this.combatTimer--;
            }
        }

        if (this.getAttackTarget() != null) {
            bossAbility(this.getAttackTarget());
        } else {
            this.bossTimer = leapCooldown;
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movementSpeedAttribute);
        }
    }

    // Disable fall damage so the boss doesn't kill itself when it leaps
    @SubscribeEvent
    public void LivingFallEvent(LivingFallEvent event) {
        if (event.getEntity() instanceof KingSlime) {
            event.setDistance(0.0F);
            event.setCanceled(true);
        }
    }
}
