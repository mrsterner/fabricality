package dev.sterner.registry

import dev.sterner.Fabricality
import dev.sterner.blocks.entity.ExtractorMachineBlockEntity
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType


object FabricalityBlockEntityTypes {
    fun register() {
        registerBlockEntityType("extractor", ExtractorMachineBlockEntity.TYPE)
        FluidStorage.SIDED.registerForBlockEntity({ tank, direction -> tank.storage },
            ExtractorMachineBlockEntity.TYPE)
    }

    private fun registerBlockEntityType(name: String, blockEntityType: BlockEntityType<*>) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Fabricality.id(name), blockEntityType)
    }
}