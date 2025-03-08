package fr.iglee42.yoyos.common.init;

import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class YoyosEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE,Yoyos.MODID);

    public static DeferredHolder<EntityType<?>,EntityType<YoyoEntity>> YOYO = ENTITY_TYPES.register("yoyo",()->EntityType.Builder.<YoyoEntity>of(YoyoEntity::new, MobCategory.MISC)
            .noSummon()
            .sized(0.25f, 0.25f)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build("yoyo"));

}
