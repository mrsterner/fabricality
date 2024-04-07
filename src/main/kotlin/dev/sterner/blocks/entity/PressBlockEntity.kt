package dev.sterner.blocks.entity

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour
import com.simibubi.create.foundation.utility.animation.LerpedFloat
import dev.sterner.registry.FabricalityBlocks
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class PressBlockEntity(type: BlockEntityType<PressBlockEntity?>?, pos: BlockPos, blockState: BlockState) : SmartBlockEntity(type, pos,
    blockState), SidedStorageBlockEntity {

    var tank: SmartFluidTankBehaviour? = null
    var fluidLevel: LerpedFloat? = null

    
    override fun tick() {
        super.tick()
        if (fluidLevel != null) fluidLevel!!.tickChaser()
    }

    override fun addBehaviours(behaviours: MutableList<BlockEntityBehaviour>?) {
        tank = SmartFluidTankBehaviour.single(this, FluidConstants.INGOT)
        behaviours!!.add(tank!!)
    }

    override fun getFluidStorage(face: Direction?): Storage<FluidVariant>? {
        if (face != Direction.UP) {
            return tank!!.capability
        }
        return null
    }
}