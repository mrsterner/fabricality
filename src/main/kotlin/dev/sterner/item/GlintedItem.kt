package dev.sterner.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class GlintedItem(properties: Properties) : Item(properties) {

    override fun isFoil(stack: ItemStack): Boolean {
        return true
    }
}