package dev.sterner.tweak

import com.simibubi.create.AllRecipeTypes
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe
import com.simibubi.create.content.kinetics.millstone.MillingRecipe
import com.simibubi.create.content.processing.basin.BasinRecipe
import com.simibubi.create.content.processing.recipe.HeatCondition
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import com.simibubi.create.foundation.fluid.FluidIngredient
import dev.sterner.Fabricality
import dev.sterner.data.FreePRP
import dev.sterner.util.RecipeUtil
import ho.artisan.lib.recipe.api.RecipeLoadingEvents
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack
import me.steven.indrev.recipes.machines.PulverizerRecipe
import me.steven.indrev.recipes.machines.entries.InputEntry
import me.steven.indrev.recipes.machines.entries.OutputEntry
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import kotlin.random.Random

object OreProcessingTweaks {
    fun register(handler: RecipeLoadingEvents.AddRecipesCallback.RecipeHandler) {
        for (entry in OreProcessingEntry.values()) {
            // Crushed -> Nugget
            handler.register(
                id(entry, entry.getCrushedOre(), "smelting")
            ) { id ->
                SmeltingRecipe(
                    id, "",
                    CookingBookCategory.MISC,
                    Ingredient.of(entry.crushedOreItem),
                    ItemStack(entry.nuggetItem, 3),
                    0.1f, 100
                )
            }
            handler.register(
                id(entry, entry.getCrushedOre(), "blasting")
            ) { id ->
                BlastingRecipe(
                    id, "",
                    CookingBookCategory.MISC,
                    Ingredient.of(entry.crushedOreItem),
                    ItemStack(entry.nuggetItem, 3),
                    0.1f, 50
                )
            }

            // Crushed -> Dust
            handler.register(
                id(entry, entry.getCrushedOre(), "milling")
            ) { id ->
                MillingRecipe(FreePRP(id)
                    .setIngredient(Ingredient.of(entry.crushedOreItem))
                    .setResult(ProcessingOutput(ItemStack(entry.dustItem, 3), 1f))
                    .setProcessingTime(200))
            }
            handler.register(
                id(entry, entry.getCrushedOre(), "crushing")
            ) { id ->
                CrushingRecipe(
                    FreePRP(id).setIngredient(
                        Ingredient.of(entry.crushedOreItem)
                    ).setResult(
                        ProcessingOutput(ItemStack(entry.dustItem, 3), 1f),
                        ProcessingOutput(ItemStack(entry.dustItem, 3), 0.5f)
                    ).setProcessingTime(200)
                )
            }

            handler.register(
                id(entry, entry.getCrushedOre(), "pulverizing")
            ) { id ->
                val inputs = arrayOf(InputEntry(Ingredient.of(entry.crushedOreItem), 1))
                val outputs = arrayOf(OutputEntry(ItemStack(entry.dustItem, 6), 1.0))
                PulverizerRecipe(id, inputs, outputs, 45)
            }

            // Dust -> Nugget
            handler.register(
                id(entry, entry.getNugget(), "smelting")
            ) { id ->
                SmeltingRecipe(
                    id, "",
                    CookingBookCategory.MISC,
                    Ingredient.of(entry.dustItem),
                    ItemStack(entry.nuggetItem), 0f, 40
                )
            }
            handler.register(
                id(entry, entry.getNugget(), "blasting")
            ) { id ->
                BlastingRecipe(
                    id, "",
                    CookingBookCategory.MISC,
                    Ingredient.of(entry.dustItem),
                    ItemStack(entry.nuggetItem), 0f, 20
                )
            }
            handler.register(
                id(entry, entry.getNugget(), "splashing")
            ) { id ->
                SplashingRecipe(FreePRP(id)
                    .setIngredient(Ingredient.of(entry.dustItem))
                    .setResult(ProcessingOutput(ItemStack(entry.nuggetItem, 2), 1f)))
            }

            /*
            handler.register(
            recipeId("milling", "emerald")
        ) { id ->
            MillingRecipe(
                FreePRP(id)
                    .setIngredient(MC.asIngredient("emerald"))
                    .setResult(FAB.asProcessingOutput("emerald_dust"))
                    .setProcessingTime(450)
            )
        }
        fun generateMelting(
            input: ResourceLocation, fluid: ResourceLocation,
            amount: Long,
            @Nullable byproduct: ResourceLocation?,
            byAmount: Long, temperature: Int, time: Int
    ): JsonObject {
             */
            // Dust -> Molten Metal
            handler.register(
                id(entry, entry.getMoltenMetal(), "melting")
            ) { id ->
                RecipeManager.fromJson(id, RecipeUtil.generateMelting(entry.getDust(),
                    entry.getMoltenMetal(),
                    FluidConstants.NUGGET / 4,
                    true))
            }

            // Ingot -> Dust
            handler.register(
                id(entry, entry.getIngot(), "crushing")
            ) { id ->
                CrushingRecipe(FreePRP(id)
                    .setIngredient(Ingredient.of(entry.ingotItem))
                    .setResult(ProcessingOutput(ItemStack(entry.dustItem), 1f))
                    .setProcessingTime(400))
            }
        }
    }

    fun register(handler: RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler) {
        for (entry in OreProcessingEntry.values()) {
            // Remove logic here
        }
    }


    private fun id(entry: OreProcessingEntry, input: ResourceLocation, type: String): ResourceLocation {
        return Fabricality.id("tweaks", "ore_processing", entry.getId().path, type, input.path)
    }

    private fun getByProduct(entry: OreProcessingEntry): OreProcessingEntry {
        val entries = OreProcessingEntry.entries.toMutableList().apply { remove(entry) }
        val random = Random(entry.hashCode())
        return entries[random.nextInt(entries.size)]
    }
}