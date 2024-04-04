package dev.sterner.item

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.TieredItem
import net.minecraft.world.item.Vanishable

class SawItem(tier: Tier, properties: Properties) : TieredItem(tier, properties), Vanishable {

    override fun getRecipeRemainder(stack: ItemStack): ItemStack {
        val ret = stack.copy()
        ret.damageValue = (ret.damageValue + 1)
        return if (ret.damageValue >= stack.maxDamage) ItemStack.EMPTY else ret
    }
}