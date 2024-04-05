package dev.sterner.data

import dev.sterner.Fabricality
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.world.level.block.Block

object FluidBlockStatesGenerator {
    fun simple(block: Block?, id: String?): MultiVariantGenerator {
        return FBlockStatesGenerator.simple(block, Fabricality.id("block", "fluid", id))
    }
}