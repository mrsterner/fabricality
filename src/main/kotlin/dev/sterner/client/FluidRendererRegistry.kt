package dev.sterner.client

import dev.sterner.Fabricality
import dev.sterner.fluid.IFluid
import dev.sterner.registry.FabricalityFluids.REDSTONE
import dev.sterner.registry.FabricalityFluids.RESIN
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import javax.annotation.Nullable


object FluidRendererRegistry {
    fun register(name: String, texture: String, still: Fluid?, flowing: Fluid?, flow: Boolean) {
        val color: Int = FluidColorRegistry.get(name)
        val stillId: ResourceLocation = Fabricality.id("fluid/" + texture + "/" + texture + "_still")
        val flowingId: ResourceLocation = Fabricality.id("fluid/" + texture + "/" + texture + "_flowing")

        /*
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)
				.register((atlasTexture, registry) -> {
					registry.register(stillId);
					registry.register(flowingId);
				});

		 */
        val fluidSprites: Array<TextureAtlasSprite?> = arrayOfNulls(2)
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
            .registerReloadListener(object : SimpleSynchronousResourceReloadListener {
                override fun getFabricId(): ResourceLocation {
                    return Fabricality.id(name + "_fluid_renderer_reloader")
                }

                override fun onResourceManagerReload(resourceManager: ResourceManager) {
                    val atlas: java.util.function.Function<ResourceLocation, TextureAtlasSprite> = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)

                    fluidSprites[0] = atlas.apply(stillId)
                    fluidSprites[1] = atlas.apply(flowingId)
                }
            })

        val handler: FluidRenderHandler = object : FluidRenderHandler {
            override fun getFluidSprites(@Nullable view: BlockAndTintGetter?,
                                         @Nullable pos: BlockPos?,
                                         state: FluidState?): Array<TextureAtlasSprite?> {
                return fluidSprites
            }

            override fun getFluidColor(@Nullable view: BlockAndTintGetter?,
                                       @Nullable pos: BlockPos?,
                                       state: FluidState?): Int {
                return if (color < 0) super.getFluidColor(view, pos, state) else color
            }
        }

        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, handler)
        if (flow) BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), still, flowing)
        else BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), still)
    }

    fun register(name: String, still: Fluid?, flowing: Fluid?, flow: Boolean) {
        register(name, name, still, flowing, flow)
    }

    fun renderFluidInit() {
        renderFluids(RESIN, REDSTONE)
        //renderFluids(MOLTEN_DESH, MOLTEN_DESH_FLOWING, MOLTEN_OSTRUM, MOLTEN_OSTRUM_FLOWING, MOLTEN_CALORITE, MOLTEN_CALORITE_FLOWING)
    }

    private fun renderFluid(fluid: Fluid) {
        if (fluid.isSource(null)) {
            val ifluid = fluid as IFluid
            ifluid.setupRendering()
        }
    }

    private fun renderFluids(vararg fluids: Fluid) {
        for (fluid in fluids) renderFluid(fluid)
    }

    private fun renderFluids(fluids: List<Fluid>) {
        for (fluid in fluids) renderFluid(fluid)
    }
}