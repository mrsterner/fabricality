package dev.sterner.blocks

import dev.sterner.Fabricality
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.world.level.material.MapColor
import java.util.function.Supplier
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.SoundType


enum class MachineBlockEntry(
        private val typeName: String,
        private val type: Supplier<RenderType>,
        private val block: AbstractMachineBlock
) {


    ANDESITE(
        "andesite",
        Supplier<RenderType> { RenderType.solid() },
        ComplexMachineBlock(FabricBlockSettings.create()
            .mapColor(MapColor.COLOR_BROWN)
            .strength(1.5f, 6.0f))
    ),

    BRASS(
        "brass",
        Supplier<RenderType> { RenderType.translucent() },
        ComplexMachineBlock(FabricBlockSettings.create().nonOpaque()
            .mapColor(MapColor.COLOR_YELLOW)
            .strength(3.0f, 6.0f))
    ),

    COPPER(
        "copper",
        Supplier<RenderType> { RenderType.cutout() },
        ComplexMachineBlock(FabricBlockSettings.create()
            .mapColor(MapColor.COLOR_ORANGE)
            .strength(3.0f, 6.0f)
            .sounds(SoundType.COPPER))
    ),

    ZINC(
        "zinc",
        Supplier<RenderType> { RenderType.cutout() },
        SolidMachineBlock(FabricBlockSettings.create()
            .mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
            .strength(3.0f, 6.0f))
    ),

    OBSIDIAN(
        "obsidian",
        Supplier<RenderType> { RenderType.translucent() },
        ComplexMachineBlock(FabricBlockSettings.create()
            .mapColor(MapColor.COLOR_BLACK)
            .strength(3.0f, 6.0f))
    ),

    ENDERIUM(
        "enderium",
        Supplier<RenderType> { RenderType.cutout() },
        CutoffMachineBlock(FabricBlockSettings.create()
            .mapColor(MapColor.WARPED_STEM)
            .strength(3.0f, 6.0f))
    );

    override fun toString(): String {
        return typeName
    }

    fun getRenderType(): RenderType {
        return type.get()
    }

    fun getBlock(): AbstractMachineBlock {
        return block
    }

    fun getId(): ResourceLocation {
        return Fabricality.id(typeName + "_machine")
    }
}