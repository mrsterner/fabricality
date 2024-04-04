package dev.sterner.interfaces

import net.minecraft.world.item.Item

interface BlockItemSettable {
    fun getSettings(): Item.Properties?
}