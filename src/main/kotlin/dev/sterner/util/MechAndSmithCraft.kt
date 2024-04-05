package dev.sterner.util

import dev.sterner.Fabricality
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import ho.artisan.lib.recipe.mixin.TransformSmithingRecipeAccessor
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.SmithingTransformRecipe
import net.minecraft.world.item.crafting.StonecutterRecipe
import java.util.function.Consumer
import javax.annotation.Nullable


object MechAndSmithCraft {
    private val entries = ArrayList<Entry>()

    /**
     * Add a MechAndSmithCraft entry for the given item.
     * @param entry the entry to add
     */
    fun addEntry(entry: Entry) {
        entries.add(entry)
    }

    fun register(handler: AddRecipesCallback.RecipeHandler) {

        entries.forEach { entry ->
            if (entry.isSmithing()) {
                handler.register(
                    id(entry, "smithing")
                ) { id ->
                    SmithingTransformRecipe(
                        id,
                        Ingredient.EMPTY,
                        Ingredient.of(entry.getInputItem()),
                        Ingredient.of(entry.getOtherItem()),
                        entry.getOutputStack()
                    )
                }
                handler.register(
                    id(entry, "mechanical_crafting")
                ) { id ->
                    RecipeUtil.mechanicalFromShaped(
                        id,
                        handler.registryManager,
                        VanillaRecipeBuilders
                            .shapedRecipe("AB")
                            .ingredient('A', Ingredient.of(entry.getInputItem()))
                            .ingredient('B', Ingredient.of(entry.getOtherItem()))
                            .output(entry.getOutputStack())
                            .build(id, ""),
                        true
                    )
                }
            } else {
                handler.register(
                    id(entry, "stonecutting")
                ) { id ->
                    StonecutterRecipe(
                        id,
                        "",
                        Ingredient.of(entry.getInputItem()),
                        entry.getOutputStack()
                    )
                }
            }
        }
    }

    fun register(handler: RemoveRecipesCallback.RecipeHandler) {
        entries.forEach(Consumer<Entry> { entry: Entry ->
            handler.removeIf { p: Recipe<*> ->
                (!ModCompat.Entry.FAB.checkContains(handler, p)
                        && p.getResultItem(handler.registryManager).`is`(entry.getOutputItem()))
            }
        })
    }

    private fun id(entry: Entry, type: String): ResourceLocation {
        return Fabricality.id("threads", entry.level, "tweak", type, entry.output.path)
    }

    fun entry(level: String, input: ResourceLocation, output: ResourceLocation, count: Int, @Nullable other: ResourceLocation?): Entry {
        return Entry(level, input, output, count, other)
    }

    data class Entry(
            val level: String,
            val input: ResourceLocation,
            val output: ResourceLocation,
            val count: Int,
            val other: ResourceLocation? = null
    ) {
        fun isSmithing(): Boolean {
            return other != null
        }

        fun getInputItem(): Item? {
            return BuiltInRegistries.ITEM[input]
        }

        fun getOutputItem(): Item? {
            return BuiltInRegistries.ITEM[output]
        }

        fun getOtherItem(): Item? {
            return other?.let { BuiltInRegistries.ITEM[it] }
        }

        fun getOutputStack(): ItemStack {
            return ItemStack(getOutputItem(), count)
        }
    }
}