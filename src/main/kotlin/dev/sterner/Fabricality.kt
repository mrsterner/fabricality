package dev.sterner

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory


object Fabricality : ModInitializer {
	val MODID = "fabricality"
    private val LOGGER = LoggerFactory.getLogger(MODID)

	override fun onInitialize() {
		registerResources()
	}

	private fun registerResources() {
		val gearwork = ResourceLocation(MODID, MODID)
		FabricLoader.getInstance().getModContainer(MODID).ifPresent { container ->
			ResourceManagerHelper.registerBuiltinResourcePack(gearwork,
				container,
				"Indrev: Retexture",
				ResourcePackActivationType.NORMAL)
		}
	}

	fun id(name: String): ResourceLocation{
		return ResourceLocation(MODID, name)
	}
}