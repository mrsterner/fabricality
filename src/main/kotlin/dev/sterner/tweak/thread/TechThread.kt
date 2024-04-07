package dev.sterner.tweak.thread

import dev.sterner.Fabricality
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.*
import net.minecraft.resources.ResourceLocation


interface TechThread {

    companion object {
        val THREADS: List<TechThread> = listOf(
            AndesiteThread,
            BrassThread,
            CopperThread,
            ZincThread,
            ObsidianThread,
            FluixThread,
            UncategorizedThread
            /*
            InvarThread,
            EnderiumThread,
            MathThread
             */
        )
    }

    fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {}

    fun modifyRecipes(handler: ModifyRecipesCallback.RecipeHandler) {}

    fun removeRecipes(handler: RemoveRecipesCallback.RecipeHandler) {}

    fun load() {}

    fun getLevel(): String

    fun recipeId(type: String, id: String): ResourceLocation {
        return Fabricality.id("thread/" + this.getLevel() + "/" + type + "/" + id)
    }
}