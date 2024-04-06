package dev.sterner.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import java.util.*


object CobbleGenUtil {
    private val COBBLE_GEN_BLOCK_MAP: Map<Block, Block> = java.util.Map.of(
        Blocks.PACKED_ICE, Blocks.ANDESITE)

    @JvmStatic
    fun getBlock(world: LevelAccessor, pos: BlockPos): BlockState {
        val upState: BlockState = world.getBlockState(pos.relative(Direction.UP))
        return if (COBBLE_GEN_BLOCK_MAP.containsKey(upState.block)
        ) COBBLE_GEN_BLOCK_MAP[upState.block]!!.defaultBlockState()
        else COBBLE_GEN_BLOCK_MAP.values.stream()
            .toList()[Random().nextInt(COBBLE_GEN_BLOCK_MAP.size)].defaultBlockState()
    }
}