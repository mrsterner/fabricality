package dev.sterner.item

import net.minecraft.world.item.Tier
import net.minecraft.world.item.Tiers

enum class ToolMaterialIndex(val typeName: String, material: Tier) {
    STONE("stone", Tiers.STONE),
    IRON("iron", Tiers.IRON),
    DIAMOND("diamond", Tiers.DIAMOND),
    NETHERITE("netherite", Tiers.NETHERITE);

    private val material: Tier = material

    fun getMaterial(): Tier {
        return material
    }
}