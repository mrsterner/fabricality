package dev.sterner.data

import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

object FBlockStatesGenerator {
    fun simple(block: Block?, modelId: ResourceLocation?): MultiVariantGenerator {
        return BlockModelGenerators.createSimpleBlock(block, modelId)
    }

    fun northDefaultHorizontal(block: Block?, modelId: ResourceLocation?): MultiVariantGenerator {
        return simple(block, modelId)
            .with(BlockModelGenerators.createHorizontalFacingDispatch())
    }
}