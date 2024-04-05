package dev.sterner.fluid

import dev.sterner.Fabricality
import dev.sterner.data.ItemModelGenerator
import dev.sterner.registry.FabricalityItems
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState


interface IFluid {
    @Environment(EnvType.CLIENT)
    fun setupRendering()

    fun hasBucketItem(): Boolean

    val id: ResourceLocation?

    val typical: Fluid

    val flowing: Fluid?

    val name: String

    fun toBlockState(state: FluidState?): BlockState?

    fun toBlockState(): BlockState?

    fun toBlock(state: FluidState?): Block?

    fun toBlock(): Block?

    fun registerBucketItem(registry: Registry<Item?>?) {
        if (this.typical !== this || !this.hasBucketItem()) return
        val bucketId: ResourceLocation = Fabricality.id(this.name + "_bucket")
        Registry.register(registry, bucketId, BucketItem(this as Fluid, FabricItemSettings().stacksTo(1)))
        Fabricality.RRPs.CLIENT_RESOURCES.addModel(
            Fabricality.id("item/" + bucketId.getPath()),
            ItemModelGenerator.generated("item/bucket", bucketId.getPath())
        )
    }

    val textureName: String?
        get() = this.name
}