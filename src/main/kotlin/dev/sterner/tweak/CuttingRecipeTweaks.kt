package dev.sterner.tweak

import com.simibubi.create.content.kinetics.saw.CuttingRecipe
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import dev.sterner.Fabricality
import dev.sterner.data.FreePRP
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.*


object CuttingRecipeTweaks {
    fun register(handler: AddRecipesCallback.RecipeHandler) {
        Arrays.stream(WoodCuttingEntry.entries.toTypedArray()).forEach { entry ->
            if (entry.isLogExist && entry.isStrippedLogExist && entry.getLogId() != null) {
                handler.register(createId(entry.getLogId())
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        entry.getLogId(),
                        entry.getStrippedLogId(),
                        1,
                        50)
                }
            }
            if (entry.isWoodExist && entry.isStrippedWoodExist && entry.getWoodId() != null) {
                handler.register(createId(entry.getWoodId())
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        entry.getWoodId(), entry.getStrippedWoodId(), 1, 50)
                }
            }

            if (entry.isStrippedLogExist && entry.isPlankExist && entry.getStrippedLogId() != null) {
                handler.register(createId(entry.getStrippedLogId())
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        entry.getStrippedLogId(), entry.getPlankId(), 6, 50)
                }
            }

            if (entry.isStrippedWoodExist && entry.isPlankExist && entry.getStrippedWoodId() != null) {
                handler.register(createId(entry.getStrippedWoodId())
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        entry.getStrippedWoodId(), entry.getPlankId(), 6, 50)
                }
            }
            if (entry.isPlankExist && entry.isPlankSlabExist && entry.getPlankId() != null) {
                handler.register(createId(entry.getPlankId())
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        entry.getPlankId(),
                        entry.getPlankSlabId(),
                        2,
                        50)
                }
            }
        }
        /*
                handler.register(createId(ModCompat.Entry.TERRESTRIA.id("small_oak_log"))
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        Mod.Entry.TERRESTRIA.id("small_oak_log"),
                        Mod.Entry.TERRESTRIA.id("stripped_small_oak_log"),
                        1,
                        50)
                }
                handler.register(createId(Mod.Entry.TERRESTRIA.id("stripped_small_oak_log"))
                ) { id: ResourceLocation ->
                    createRecipe(id,
                        Mod.Entry.TERRESTRIA.id("stripped_small_oak_log"),
                        Mod.Entry.MC.id("oak_planks"),
                        6,
                        50)
                }

         */
    }

    private fun createRecipe(
            id: ResourceLocation, inputId: ResourceLocation,
            outputId: ResourceLocation, outputCount: Int, processingTime: Int
    ): CuttingRecipe {
        return CuttingRecipe(
            FreePRP(id)
                .setIngredient(Ingredient.of(BuiltInRegistries.ITEM.get(inputId)))
                .setResult(ProcessingOutput(ItemStack(BuiltInRegistries.ITEM.get(outputId), outputCount), 1f))
                .setProcessingTime(processingTime)
        )
    }

    private fun createId(input: ResourceLocation): ResourceLocation {
        return Fabricality
            .id(("tweaks/wood_cutting/" + input.namespace).toString() + "/" + input.path)
    }
}