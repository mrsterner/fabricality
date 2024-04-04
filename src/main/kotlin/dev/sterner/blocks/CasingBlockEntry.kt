package dev.sterner.blocks

import com.simibubi.create.content.decoration.encasing.CasingBlock
import dev.sterner.Fabricality
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.material.MapColor
import java.util.function.Supplier


enum class CasingBlockEntry(
        private val typeName: String,
        private val renderType: Supplier<RenderType>,
        private val block: CasingBlock) {

    ZINC(
        "zinc", Supplier<RenderType> { RenderType.solid() },
        SimpleCasingBlock(FabricBlockSettings.create()
            .mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
            .strength(2.0f, 6.0f),
            "zinc")
    ),

    INVAR(
        "invar", Supplier<RenderType> { RenderType.solid() },
        SimpleCasingBlock(FabricBlockSettings.create()
            .mapColor(MapColor.COLOR_GRAY)
            .strength(2.0f, 6.0f),
            "invar")
    ),

    FLUIX(
        "fluix", Supplier<RenderType> { RenderType.solid() },
        SimpleCasingBlock(FabricBlockSettings.create()
            .mapColor(MapColor.LAPIS)
            .strength(2.0f, 6.0f),
            "fluix")
    ),

    ENDERIUM(
        "enderium", Supplier<RenderType> { RenderType.solid() },
        SimpleCasingBlock(FabricBlockSettings.create()
            .mapColor(MapColor.WARPED_STEM)
            .strength(2.0f, 6.0f),
            "enderium")
    );

    override fun toString(): String {
        return typeName
    }

    fun getRenderType(): RenderType {
        return renderType.get()
    }

    fun getBlock(): CasingBlock {
        return block
    }

    fun getId(): ResourceLocation {
        return Fabricality.id(typeName + "_casing")
    }
}