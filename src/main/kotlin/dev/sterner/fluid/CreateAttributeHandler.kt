package dev.sterner.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level

@JvmRecord
data class CreateAttributeHandler(val name: Component, val viscosity: Int, val lighterThanAir: Boolean) :
    FluidVariantAttributeHandler {

    constructor(key: String, viscosity: Int, density: Int) : this(Component.translatable(key),
        viscosity,
        density <= 0)

    constructor(key: String) : this(key, FluidConstants.WATER_VISCOSITY, 1000)

    override fun getName(fluidVariant: FluidVariant): Component {
        return name.copy()
    }

    override fun getViscosity(variant: FluidVariant, world: Level?): Int {
        return viscosity
    }

    override fun isLighterThanAir(variant: FluidVariant): Boolean {
        return lighterThanAir
    }
}