package dev.sterner.blocks

import com.simibubi.create.AllShapes
import dev.sterner.blocks.entity.PressBlockEntity
import dev.sterner.registry.FabricalityBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class PressBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return PressBlockEntity(FabricalityBlockEntityTypes.PRESS.get(), pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun getShape(blockState: BlockState?, blockGetter: BlockGetter?, blockPos: BlockPos?, ctx: CollisionContext?): VoxelShape {
        return AllShapes.CASING_13PX[Direction.UP]
    }
}