package dev.sterner.tweak.thread

import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.addEntry
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.util.RecipeUtil.donutRecipe
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.Nullable
import dev.sterner.ModCompatHelper.Entry.*


object ObsidianThread : TechThread{

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        handler.register(
            recipeId("crafting", "obsidian_machine")
        ) { id: ResourceLocation? ->
            donutRecipe(
                id,
                CREATE.asItem("railway_casing"),
                FAB.asItem("sturdy_mechanism"),
                FAB.asItem("obsidian_machine"),
                1
            )
        }
    }

    override fun load() {
        addEntry(entry(CREATE.id("track_station"), 1, null))
        addEntry(entry(CREATE.id("track_signal"), 1, CREATE.id("electron_tube")))
        addEntry(entry(CREATE.id("track_observer"), 1, MC.id("observer")))
        addEntry(entry(CREATE.id("controls"), 1, MC.id("lever")))
    }

    override fun getLevel(): String {
        return "obsidian"
    }

    @Contract("_, _, _ -> new")
    private fun entry(output: ResourceLocation, count: Int, @Nullable other: ResourceLocation?): MechAndSmithCraft.Entry {
        return entry(this.getLevel(), FAB.id("obsidian_machine"), output, count, other)
    }
}