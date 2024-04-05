package dev.sterner

import dev.sterner.Fabricality.RRPs
import dev.sterner.blocks.CasingBlockEntry
import dev.sterner.blocks.MachineBlockEntry
import dev.sterner.client.FluidRendererRegistry
import dev.sterner.tweak.OreProcessingEntry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.server.packs.PackResources
import pers.solid.brrp.v1.fabric.api.RRPCallback
import java.util.*


object FabricalityClient : ClientModInitializer {

	override fun onInitializeClient() {

		FluidRendererRegistry.renderFluidInit()
		//ColorRegistryListener.load()
		//WoodCuttingEntry.checkAll()
		OreProcessingEntry.checkAll()

		Arrays.stream(MachineBlockEntry.entries.toTypedArray())
			.forEach { entry -> BlockRenderLayerMap.INSTANCE.putBlock(entry.getBlock(), entry.getRenderType()) }
		Arrays.stream(CasingBlockEntry.entries.toTypedArray())
			.forEach { entry -> BlockRenderLayerMap.INSTANCE.putBlock(entry.getBlock(), entry.getRenderType()) }

		RRPCallback.AFTER_VANILLA.register(RRPCallback { list: MutableList<PackResources?> -> list.add(RRPs.CLIENT_RESOURCES) })
	}
}