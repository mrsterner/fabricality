package dev.sterner.blocks.entity

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation
import dev.sterner.Fabricality
import dev.sterner.registry.FabricalityBlockEntityTypes
import dev.sterner.registry.FabricalityBlocks
import dev.sterner.registry.FabricalityFluids
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.*


class ExtractorMachineBlockEntity(pos: BlockPos, blockState: BlockState) : BlockEntity(TYPE, pos, blockState), IHaveGoggleInformation {

    val storage: SingleVariantStorage<FluidVariant> = object : SingleVariantStorage<FluidVariant>() {
        override fun getBlankVariant(): FluidVariant {
            return FluidVariant.blank()
        }

        override fun getCapacity(variant: FluidVariant): Long {
            return FluidConstants.BOTTLE * 4
        }

        override fun onFinalCommit() {
            setChanged()
        }
    }

    var ticks: Int = 0


    companion object {
        val TYPE: BlockEntityType<ExtractorMachineBlockEntity> = FabricBlockEntityTypeBuilder
            .create({ blockPos: BlockPos?, blockState: BlockState? -> ExtractorMachineBlockEntity(blockPos!!, blockState!!) },
                FabricalityBlocks.EXTRACTOR).build()
    }


    fun tick(world: Level, blockPos: BlockPos, blockState: BlockState?,
             blockEntity: ExtractorMachineBlockEntity) {
        if (!world.isClientSide()) {
            blockEntity.ticks++
            if (blockEntity.ticks >= 360) {
                blockEntity.ticks = 0

                val f = isNextToTree(world, blockPos)
                if (f > 0.0f && blockEntity.storage.amount < blockEntity.storage.capacity) {
                    TransferUtil.insert(blockEntity.storage, FluidVariant.of(FabricalityFluids.RESIN.get()),
                        (f * FluidConstants.INGOT).toLong())
                }
            }
        }
    }

    private fun isNextToTree(world: Level, blockPos: BlockPos): Float {
        checkNotNull(world)

        for (direction in Direction.entries.toTypedArray()
            .filter(({ direction -> direction !== Direction.UP && direction !== Direction.DOWN }))) {
            val targetPos = blockPos.relative(direction)
            val targetState: BlockState = world.getBlockState(targetPos)

            if (isVecLog(targetState)) {
                // check if there are enough logs
                var topBlock = targetPos
                var bottomBlock = targetPos

                // Get top and bottom coordinates of the tree
                while (isVecLog(world.getBlockState(topBlock.relative(Direction.UP, 1)))) {
                    topBlock = topBlock.relative(Direction.UP, 1)
                }

                while (isVecLog(world.getBlockState(bottomBlock.relative(Direction.DOWN, 1)))) {
                    bottomBlock = bottomBlock.relative(Direction.DOWN, 1)
                }

                // Add an extra block, since we're measuring from the top of topBlock to the bottom of bottomBlock (inclusive)
                val difference = topBlock.y - bottomBlock.y + 1

                if (difference >= 5) {

                    // check if there are leaves
                    var count = 0

                    // Get a range of 2 blocks in every direction
                    val bottomPoint = topBlock.relative(Direction.DOWN, 2).relative(Direction.WEST, 2).relative(Direction.SOUTH, 2)
                    val topPoint = topBlock.relative(Direction.UP, 2).relative(Direction.EAST, 2).relative(Direction.NORTH, 2)
                    for (value in BlockPos.betweenClosed(bottomPoint, topPoint)) {
                        if (isPersistentLeaves(world.getBlockState(value))) count++
                    }

                    if (count > 4) {
                        return if (isRubberTree(world.getBlockState(targetPos))) 1.5f else 1.0f
                    }
                }
            }
        }
        return 0.0f
    }

    private fun isPersistentLeaves(state: BlockState): Boolean {
        return state.`is`(BlockTags.LEAVES) && !state.getValue(LeavesBlock.PERSISTENT)
    }

    private fun isVecLog(blockState: BlockState): Boolean {
        if (blockState.block is RotatedPillarBlock) {
            var block = blockState.block as RotatedPillarBlock
            var bl: Boolean = BuiltInRegistries.BLOCK.getTag(BlockTags.LOGS).get().stream().anyMatch { blockHolder -> blockHolder.value() === block }
            var bl2 = blockState.getValue(RotatedPillarBlock.AXIS) === Direction.Axis.Y
            return bl && bl2
        }
        return false
    }

    private fun isRubberTree(state: BlockState): Boolean {
        return BuiltInRegistries.BLOCK.getKey(state.block).getPath().contains("rubber")
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        storage.variant = FluidVariant.fromNbt(tag.getCompound("fluid"))
        storage.amount = tag.getLong("amount")

        ticks = tag.getInt("ticks")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("fluid", storage.variant.toNbt())
        tag.putLong("amount", storage.amount)

        tag.putInt("ticks", ticks)
    }

    override fun addToGoggleTooltip(tooltip: List<Component?>?, isPlayerSneaking: Boolean): Boolean {
        return true
    }
}