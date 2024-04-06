package dev.sterner.util

import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack



object ItemStackUtil {
    fun replaceItemStack(oldStack: ItemStack, newStack: ItemStack?, count: Int, player: Player, hand: InteractionHand?) {
        if (oldStack.count == count) {
            player.setItemInHand(hand, newStack)
        } else if (oldStack.count > count) {
            oldStack.count = oldStack.count - count
            player.addItem(newStack)
        }
    }
}