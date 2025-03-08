package fr.iglee42.yoyos.common.init;

import com.mojang.serialization.Codec;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.EnchantmentsState;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class YoyosDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Yoyos.MODID);


    public static final DataComponentType<Boolean> ATTACK = register("attack",b->b.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<EnchantmentsState> ENCHANTMENTS = register("enchantments",b->b.persistent(EnchantmentsState.CODEC).networkSynchronized(EnchantmentsState.STREAM_CODEC));

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }
}


