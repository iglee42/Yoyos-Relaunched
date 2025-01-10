package fr.iglee42.yoyos.common.api;

import fr.iglee42.yoyos.common.YoyoEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


/**
 * Represents a factory function for creating a YoyoEntity.
 */
@FunctionalInterface
public interface YoyoFactory {
    YoyoEntity create(Level world, Player player, InteractionHand hand);
}
