package dev.sterner

import com.simibubi.create.Create
import com.simibubi.create.foundation.data.CreateRegistrate
import dev.sterner.registry.FabricalityBlocks
import dev.sterner.registry.FabricalityFluids
import dev.sterner.registry.FabricalityItems
import dev.sterner.tweak.RecipeTweaks
import dev.sterner.tweak.thread.TechThread
import ho.artisan.lib.recipe.api.RecipeLoadingEvents
import it.unimi.dsi.fastutil.objects.ReferenceArrayList
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackResources
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import org.jetbrains.annotations.Contract
import org.slf4j.LoggerFactory
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.fabric.api.RRPCallback
import java.util.function.Predicate


object Fabricality : ModInitializer {



	//TODO
	/*
	Fluix crystal recipe, since seeds no longer exists
	Recipe for liquid redstone
	 */

	val debug = true
	val MODID = "fabricality"
	val REGISTRATE: CreateRegistrate = CreateRegistrate.create(MODID)
    private val LOGGER = LoggerFactory.getLogger(MODID)

	val GENERAL_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB, ResourceLocation(
			MODID,
			MODID
		)
	)

	override fun onInitialize() {
		FabricalityItems.register()
		FabricalityBlocks.register()
		FabricalityFluids.register()

		REGISTRATE.register()

		ItemGroupEvents.modifyEntriesEvent(GENERAL_KEY).register { content ->
			content.accept(FabricalityFluids.REDSTONE.get().bucket)
			content.accept(FabricalityFluids.RESIN.get().bucket)
			content.accept(FabricalityFluids.WASTE.get().bucket)
			content.accept(FabricalityFluids.SKY_STONE.get().bucket)
			content.accept(FabricalityFluids.FINE_SAND.get().bucket)
			content.accept(FabricalityFluids.COKE.get().bucket)
			content.accept(FabricalityFluids.RAW_LOGIC.get().bucket)
			content.accept(FabricalityFluids.SOUL.get().bucket)
		}

		Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB, GENERAL_KEY, FabricItemGroup.builder()
				.icon { BuiltInRegistries.ITEM.get(id("andesite_machine")).defaultInstance }
				.title(Component.literal("Fabricality"))
				.build())

		for (thread in TechThread.THREADS) thread.load()

		registerEvents()
		registerResources()

		RRPCallback.AFTER_VANILLA.register(RRPCallback { list: MutableList<PackResources?> -> list.add(RRPs.SERVER_RESOURCES) })
	}

	private fun registerEvents() {
		RecipeLoadingEvents.ADD.register(RecipeTweaks())
		RecipeLoadingEvents.MODIFY.register(RecipeTweaks())
		RecipeLoadingEvents.REMOVE.register(RecipeTweaks())
	}

	object RRPs {
		val CLIENT_RESOURCES: RuntimeResourcePack = RuntimeResourcePack.create(id("client_resources"))
		val SERVER_RESOURCES: RuntimeResourcePack = RuntimeResourcePack.create(id("server_resources"))
	}

	private fun registerResources() {
		debug()
		val gearwork = ResourceLocation(MODID, MODID)
		FabricLoader.getInstance().getModContainer(MODID).ifPresent { container ->
			ResourceManagerHelper.registerBuiltinResourcePack(gearwork,
				container,
				"Indrev: Retexture",
				ResourcePackActivationType.DEFAULT_ENABLED)
		}
	}

	fun debug(){
		if (debug) {
			LOGGER.info("FABRICALITY DEBUG: ${Thread.currentThread().stackTrace[2].methodName}")
		}
	}

	@Contract("_ -> new")
	fun id(vararg paths: String?): ResourceLocation {
		return ResourceLocation(MODID, java.lang.String.join("/", *paths))
	}

	fun id(name: String): ResourceLocation{
		return ResourceLocation(MODID, name)
	}
}