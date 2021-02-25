package MICDeps.util.handlers;

import com.mic.betterslimes.entity.EntityBetterSlime;
import com.mic.betterslimes.entity.ISpecialSlime;
import com.mic.betterslimes.entity.slimes.*;

import MICDeps.ModBase;
import MICDeps.items.ItemBuilder;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RegistryHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Item item : ItemBuilder.getItemList()) {
			ModBase.proxy.registerItemRenderer(item, 0, "inventory");
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {

		if (event.getEntity() instanceof Quazar) {
			Quazar k = (Quazar) event.getEntity();

			k.setSlimeSize(9, true);
			k.setAttackModifier(1);
			k.setHealthModifier(40);
		} else if (event.getEntity() instanceof EntityBetterSlime) {

			if (!(event.getEntity() instanceof ISpecialSlime)) {
                EntityBetterSlime s = (EntityBetterSlime) event.getEntity();
                
                if (BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY)
                    .contains(event.getWorld().getBiome(event.getEntity().getPosition()))) 
                        s = new IceSlime(event.getWorld());
                
                if (BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE)
                    .contains(event.getWorld().getBiome(event.getEntity().getPosition()))) 
                        s = new JungleSlime(event.getWorld());
                
                if (BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY)
                    .contains(event.getWorld().getBiome(event.getEntity().getPosition()))) 
                        s = new SandSlime(event.getWorld());
                
				if (!s.equals(event.getEntity())) {
					s.setLocationAndAngles(event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ,
							event.getEntity().rotationYaw, event.getEntity().rotationPitch);
					s.onInitialSpawn(event.getWorld().getDifficultyForLocation(event.getEntity().getPosition()),
							(IEntityLivingData) null);
					if (!event.getWorld().isRemote) {
						event.getWorld().spawnEntity(s);
					}
					event.getEntity().setDropItemsWhenDead(false);
					event.getEntity().setDead();
				}
			}
		}
	}
}
