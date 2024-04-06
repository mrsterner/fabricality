package dev.sterner.blocks

import dev.sterner.blocks.entity.ExtractorMachineBlockEntity
import dev.sterner.interfaces.ResourcedBlock
import dev.sterner.registry.FabricalityFluids
import dev.sterner.util.ItemStackUtil
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult


class ExtractorMachineBlock(properties: Properties) : BaseEntityBlock(properties), ResourcedBlock {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return ExtractorMachineBlockEntity(pos, state)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun <T : BlockEntity?> getTicker(
            world: Level?,
            state: BlockState?,
            type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? {
        return createTickerHelper(
            type,
            ExtractorMachineBlockEntity.TYPE
        ) { world: Level?, pos: BlockPos?, state: BlockState?, entity: ExtractorMachineBlockEntity? ->
            entity?.tick(world!!, pos!!, state, entity)
        }
    }

    override fun use(state: BlockState,
                     level: Level,
                     pos: BlockPos,
                     player: Player,
                     hand: InteractionHand,
                     hit: BlockHitResult): InteractionResult {
        if (!(hit.direction === Direction.UP || hit.direction === Direction.DOWN || BuiltInRegistries.ITEM.getKey(player.getItemInHand(
                hand).item).equals(ResourceLocation("minecraft", "bucket")))
        ) return InteractionResult.PASS
        val extractor: ExtractorMachineBlockEntity =
            checkNotNull(level.getBlockEntity(pos) as ExtractorMachineBlockEntity)
        val stack: ItemStack = player.getItemInHand(hand)
        if (extractor.storage.getAmount() >= FluidConstants.BUCKET
            && extractor.storage.resource.isOf(FabricalityFluids.RESIN.get())
        ) {
            TransferUtil.extract<FluidVariant>(extractor.storage,
                FluidVariant.of(FabricalityFluids.RESIN.get()),
                FluidConstants.BUCKET)
            ItemStackUtil.replaceItemStack(stack, ItemStack(FabricalityFluids.RESIN.get().bucket), 1, player, hand)
            return InteractionResult.SUCCESS
        }
        return InteractionResult.PASS
    }

    override fun doLootTable(): Boolean {
        return true
    }

    override fun doItemModel(): Boolean {
        return false
    }
}