package MICDeps.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public interface IProxy {
    default void preInit(FMLPreInitializationEvent event) {}
    default void init(FMLInitializationEvent event) {}
    default void registerItemRenderer(Item item, int meta, String id) {}
    default void spawnParticle(World worldIn, EnumParticleTypes types, double posX, double posY, double posZ, double d1, double d2, double d3) {}
    default void registerRenders() {}
    
    String localize(String unlocalized, Object... args);
}