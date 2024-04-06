package dev.sterner.client.plugin.emi

import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.stack.EmiStack
import dev.sterner.tweak.RecipeTweaks
import dev.sterner.ModCompatHelper
import java.util.*

class FabEMIClientPlugin : EmiPlugin {

    private fun joinAll(vararg subs: String): String {
        if (subs.isEmpty()) return ""
        return Arrays.stream(subs).filter(Objects::nonNull).filter { s: String -> s.isNotEmpty() }
            .reduce { f: String, s: String -> f + "_" + s }
            .orElse(subs[0])
    }

    override fun register(registry: EmiRegistry?) {
        RecipeTweaks.DEPRECATED_ITEMS.forEach { registry!!.removeEmiStacks(EmiStack.of(it)) }

        val postfix = arrayOf("pickaxe", "axe", "shovel", "hoe", "sword")

        Arrays.stream(arrayOf("tin", "copper", "steel", "bronze", "lead", "silver"
        )).forEach { prefix ->
            Arrays.stream(postfix)
                .forEach {
                    registry!!.removeEmiStacks(EmiStack.of(ModCompatHelper.Entry.INDREV.asItem(joinAll(prefix, it))))
                }
        }
    }
}