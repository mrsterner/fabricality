package dev.sterner.util

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*


object RecipeUtil {
    fun donutRecipe(id: ResourceLocation?, center: Item?, other: Item?, output: Item?, count: Int): ShapedRecipe {
        return VanillaRecipeBuilders.shapedRecipe("OOO", "OXO", "OOO").ingredient('X', Ingredient.of(center))
            .ingredient('O', Ingredient.of(other)).output(ItemStack(output, count)).build(id, "")
    }

    fun mechanicalFromShaped(
            id: ResourceLocation?, registryManager: RegistryAccess?,
            recipe: ShapedRecipe, acceptMirrored: Boolean
    ): MechanicalCraftingRecipe {
        return MechanicalCraftingRecipe(
            id, recipe.group, recipe.width, recipe.height,
            recipe.ingredients, recipe.getResultItem(registryManager), acceptMirrored
        )
    }

    fun generateMelting(
            input: ResourceLocation,
            fluid: ResourceLocation,
            amount: Long,
            heatRequirement: String?
    ): JsonObject {
        val json = JsonObject()

        json.addProperty("type", "create:mixing")
        json.addProperty("heatRequirement", heatRequirement)

        // Ingredients array
        val ingredientsArray = JsonArray()
        val ingredientObject = JsonObject()
        ingredientObject.addProperty("item", input.toString())
        ingredientsArray.add(ingredientObject)
        json.add("ingredients", ingredientsArray)

        // Results array
        val resultsArray = JsonArray()
        val resultObject = JsonObject()
        resultObject.addProperty("amount", amount)
        resultObject.addProperty("fluid", fluid.toString())
        resultsArray.add(resultObject)
        json.add("results", resultsArray)

        return json
    }


    fun swapRecipeOutput(
            recipe: Recipe<*>,
            output: ItemStack?, category: CraftingBookCategory?
    ): Recipe<*> {
        if (recipe is ShapelessRecipe) return swapShapelessRecipeOutput(recipe, output, category)
        if (recipe is ShapedRecipe) return swapShapedRecipeOutput(recipe, output, category)
        return recipe
    }

    @Deprecated("")
    fun swapRecipeIngredient(
            registryManager: RegistryAccess?, recipe: Recipe<*>,
            from: Ingredient?, to: Ingredient?,
            category: CraftingBookCategory?
    ): Recipe<*> {
        if (recipe is ShapelessRecipe) return swapShapelessRecipeIngredient(registryManager,
            recipe, from, to, category)
        if (recipe is ShapedRecipe) return swapShapedRecipeIngredient(registryManager,
            recipe, from, to, category)
        return recipe
    }

    fun swapShapelessRecipeOutput(
            recipe: ShapelessRecipe,
            output: ItemStack?, category: CraftingBookCategory?
    ): ShapelessRecipe {
        return ShapelessRecipe(recipe.id, recipe.group, category, output, recipe.ingredients)
    }

    @Deprecated("")
    fun swapShapelessRecipeIngredient(
            registryManager: RegistryAccess?, recipe: ShapelessRecipe,
            from: Ingredient?, to: Ingredient?, category: CraftingBookCategory?
    ): ShapelessRecipe {
        val ingredients: NonNullList<Ingredient> = recipe.ingredients
        for (i in 0 until ingredients.size) {
            if (ingredients.get(i).equals(from)) {
                ingredients.set(i, to)
            }
        }
        return ShapelessRecipe(
            recipe.id, recipe.group, category,
            recipe.getResultItem(registryManager), ingredients
        )
    }

    fun swapShapedRecipeOutput(
            recipe: ShapedRecipe, output: ItemStack?,
            category: CraftingBookCategory?
    ): ShapedRecipe {
        return ShapedRecipe(
            recipe.id, recipe.group,
            category,
            recipe.width, recipe.height,
            recipe.ingredients, output
        )
    }

    @Deprecated("")
    fun swapShapedRecipeIngredient(
            registryManager: RegistryAccess?, recipe: ShapedRecipe,
            from: Ingredient?, to: Ingredient?,
            category: CraftingBookCategory?
    ): ShapedRecipe {
        val ingredients: NonNullList<Ingredient> = recipe.ingredients
        for (i in 0 until ingredients.size) {
            if (ingredients.get(i).equals(from)) {
                ingredients.set(i, to)
            }
        }

        return ShapedRecipe(
            recipe.id, recipe.group,
            category,
            recipe.width, recipe.height,
            ingredients, recipe.getResultItem(registryManager)
        )
    }

    object JsonBuilder {
        fun itemEntry(id: ResourceLocation, count: Int): JsonObject {
            val json: JsonObject = JsonObject()
            json.addProperty("item", id.toString())
            json.addProperty("count", count)
            return json
        }

        fun itemEntry(id: ResourceLocation): JsonObject {
            val json: JsonObject = JsonObject()
            json.addProperty("item", id.toString())
            return json
        }

        fun itemEntry(id: ResourceLocation, count: Int, chance: Double): JsonObject {
            val json: JsonObject = itemEntry(id, count)
            json.addProperty("chance", chance)
            return json
        }

        fun fluidEntry(id: ResourceLocation, amount: Int): JsonObject {
            val json: JsonObject = JsonObject()
            json.addProperty("fluid", id.toString())
            json.addProperty("amount", amount)
            return json
        }

        fun fluidEntry(id: ResourceLocation, amount: Long): JsonObject {
            return fluidEntry(id, amount.toInt())
        }
    }
}