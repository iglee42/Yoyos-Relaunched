package fr.iglee42.yoyos.common.api;

import fr.iglee42.yoyos.common.YoyoEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
/**
 * Represents a function that handles interactions between a yoyo entity and a block.
 */
@FunctionalInterface
public interface BlockInteraction {
    boolean apply(ItemStack yoyoStack, Player player, BlockPos pos, BlockState state, Block block, YoyoEntity yoyo);
}
