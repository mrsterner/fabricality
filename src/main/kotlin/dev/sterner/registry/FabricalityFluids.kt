package dev.sterner.registry

import dev.sterner.Fabricality
import dev.sterner.data.FluidBlockStatesGenerator
import dev.sterner.data.FluidModelGenerator
import dev.sterner.fluid.BaseFluid
import dev.sterner.fluid.IFluid
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import javax.swing.text.html.parser.DTDConstants.NUMBERS


object FabricalityFluids {

    val RESIN: Fluid = BaseFluid("resin")
    val REDSTONE: Fluid = BaseFluid("redstone")
    val MOLTED_IRON: Fluid = BaseFluid("molten_iron")
    val MOLTED_GOLD: Fluid = BaseFluid("molten_gold")
    val MOLTED_COPPER: Fluid = BaseFluid("molten_copper")
    val MOLTED_ZINC: Fluid = BaseFluid("molten_zinc")
    val MOLTED_TIN: Fluid = BaseFluid("molten_tin")
    val MOLTED_LEAD: Fluid = BaseFluid("molten_lead")
    val MOLTED_DESH: Fluid = BaseFluid("molten_desh")
    val MOLTED_OSTRUM: Fluid = BaseFluid("molten_ostrum")
    val MOLTED_CALORITE: Fluid = BaseFluid("molten_calorite")


    fun register() {
        registerIFluids(RESIN, REDSTONE, MOLTED_IRON, MOLTED_GOLD, MOLTED_COPPER, MOLTED_ZINC, MOLTED_TIN, MOLTED_LEAD, MOLTED_DESH, MOLTED_OSTRUM, MOLTED_CALORITE)

    }

    private fun registerFluid(id: ResourceLocation?, stillId: ResourceLocation?, fluid: Fluid) {
        Registry.register(BuiltInRegistries.FLUID, id, fluid)
        if (!fluid.isSource(null)) FabricalityBlocks.registerFluidBlock(stillId, fluid as FlowingFluid)
    }

    private fun registerIFluid(fluid: Fluid) {
        val iFluid = fluid as IFluid
        registerFluid(iFluid.id, (iFluid.typical as IFluid).id, fluid)
        iFluid.registerBucketItem(BuiltInRegistries.ITEM)
        if (fluid.isSource(null)) {
            Fabricality.RRPs.CLIENT_RESOURCES.addBlockState(
                iFluid.id,
                FluidBlockStatesGenerator.simple(iFluid.toBlock(), iFluid.name)
            )
            Fabricality.RRPs.CLIENT_RESOURCES.addBlockState(
                Fabricality.id(iFluid.name + "_flowing"),
                FluidBlockStatesGenerator.simple(iFluid.toBlock(), iFluid.name)
            )
            Fabricality.RRPs.CLIENT_RESOURCES.addModel(
                Fabricality.id("block/fluid/" + iFluid.name),
                FluidModelGenerator.simple(iFluid.textureName + "_still",
                    iFluid.textureName)
            )
        }
    }

    private fun registerIFluids(vararg fluids: Fluid) {
        for (fluid in fluids) registerIFluid(fluid)
    }

    private fun registerIFluids(fluids: List<Fluid>) {
        for (fluid in fluids) registerIFluid(fluid)
    }
}