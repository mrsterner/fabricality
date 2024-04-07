package dev.sterner.client.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer
import com.simibubi.create.foundation.fluid.FluidRenderer
import dev.sterner.blocks.entity.PressBlockEntity
import dev.sterner.registry.FabricalityFluids
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.client.renderer.MultiBufferSource

class PressBlockEntityRenderer : SafeBlockEntityRenderer<PressBlockEntity>() {

    override fun renderSafe(be: PressBlockEntity?,
                            partialTicks: Float,
                            ms: PoseStack?,
                            bufferSource: MultiBufferSource?,
                            light: Int,
                            overlay: Int) {


        val renderTank: SmartFluidTankBehaviour = be?.tank ?: return

        val primaryTank = renderTank.primaryTank
        var fluidStack = primaryTank.renderedFluid
        val level = primaryTank.fluidLevel
            .getValue(partialTicks)

        fluidStack = FluidStack(FabricalityFluids.SOUL.get(), FluidConstants.BUCKET)

        if (!fluidStack.isEmpty) {
            val yMin = 3f / 16f
            val min = 2f / 16f
            val max = min + (12 / 16f)
            val yOffset = (9 / 16f)
            ms!!.pushPose()
            //ms!!.translate(0f, yOffset, 0f)
            FluidRenderer.renderFluidBox(fluidStack, min, yMin, min, max, yMin + yOffset, max, bufferSource, ms, light, false)
            ms!!.popPose()
        }
    }
}