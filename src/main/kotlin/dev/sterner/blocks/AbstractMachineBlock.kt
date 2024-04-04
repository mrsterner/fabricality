package dev.sterner.blocks

import dev.sterner.Fabricality
import dev.sterner.data.FBlockStatesGenerator
import dev.sterner.interfaces.ResourcedBlock
import dev.sterner.util.VoxelShapeMath
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape


abstract class AbstractMachineBlock(settings: Properties) : HorizontalDirectionalBlock(settings), ResourcedBlock {

    override fun getBaseBlock(): Block? {
        return this
    }

    override fun doBlockStates(): Boolean {
        return true
    }

    override fun doItemModel(): Boolean {
        return super.doItemModel()
    }

    override fun doLootTable(): Boolean {
        return true
    }

    override fun getBlockModelId(): ResourceLocation {
        return Fabricality.id("block", "machine", BuiltInRegistries.BLOCK.getKey(this).path)
    }

    override fun getBlockStates(): BlockStateGenerator {
        return if (this.isFull()
        ) FBlockStatesGenerator.simple(getBaseBlock(), getBlockModelId())
        else FBlockStatesGenerator.northDefaultHorizontal(getBaseBlock(), getBlockModelId())
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(FACING)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return VoxelShapeMath.simpleBox(1.0, 0.0, 1.0, 15.0, if (this.isFull()) 16.0 else 14.0, 15.0)
    }

    override fun updateShape(state: BlockState,
                             direction: Direction,
                             neighborState: BlockState,
                             level: LevelAccessor,
                             pos: BlockPos,
                             neighborPos: BlockPos): BlockState {
        return state
    }

    protected abstract fun isWaterLoggable(): Boolean

    protected abstract fun isFull(): Boolean
}