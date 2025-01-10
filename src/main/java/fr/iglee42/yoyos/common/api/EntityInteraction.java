package fr.iglee42.yoyos.common.api;

import fr.iglee42.yoyos.common.YoyoEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack; /**
 * Represents a function that handles interactions between a yoyo entity and another entity.
 */
@FunctionalInterface
public interface EntityInteraction {
    boolean apply(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target);
}
