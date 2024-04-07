package dev.sterner.registry

import com.simibubi.create.AllBlocks
import com.simibubi.create.Create
import com.simibubi.create.content.contraptions.actors.roller.RollerBlockEntity
import com.tterrag.registrate.util.entry.BlockEntityEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import dev.sterner.Fabricality
import dev.sterner.blocks.entity.ExtractorMachineBlockEntity
import dev.sterner.blocks.entity.PressBlockEntity
import dev.sterner.client.renderer.PressBlockEntityRenderer
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState


object FabricalityBlockEntityTypes {
    
    val PRESS: BlockEntityEntry<PressBlockEntity?> =
        Fabricality.REGISTRATE.blockEntity<PressBlockEntity?>("press"
        ) { type: BlockEntityType<PressBlockEntity?>?, pos: BlockPos, state: BlockState ->
            PressBlockEntity(type,
                pos,
                state)
        }
            .validBlocks(NonNullSupplier { FabricalityBlocks.PRESS })
            .renderer {
                NonNullFunction<BlockEntityRendererProvider.Context?, BlockEntityRenderer<in PressBlockEntity?>> { context: BlockEntityRendererProvider.Context? ->
                    PressBlockEntityRenderer()
                }
            }
            .register()
    
    fun register() {
        registerBlockEntityType("extractor", ExtractorMachineBlockEntity.TYPE)
        FluidStorage.SIDED.registerForBlockEntity({ tank, direction -> tank.storage },
            ExtractorMachineBlockEntity.TYPE)
    }

    private fun registerBlockEntityType(name: String, blockEntityType: BlockEntityType<*>) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Fabricality.id(name), blockEntityType)
    }
}