package dev.sterner.tweak

import com.google.common.collect.ImmutableList
import com.simibubi.create.AllRecipeTypes
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock
import com.simibubi.create.content.fluids.transfer.FillingRecipe
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe
import com.simibubi.create.content.kinetics.millstone.MillingRecipe
import com.simibubi.create.content.kinetics.press.PressingRecipe
import com.simibubi.create.content.processing.recipe.HeatCondition
import com.simibubi.create.foundation.fluid.FluidIngredient
import dev.sterner.Fabricality
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.data.FreePRP
import dev.sterner.tweak.thread.TechThread
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.RecipeUtil
import ho.artisan.lib.recipe.api.RecipeLoadingEvents
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import io.github.fabricators_of_create.porting_lib.tags.Tags
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.level.ItemLike
import java.util.*


class RecipeTweaks : RecipeLoadingEvents.AddRecipesCallback, RecipeLoadingEvents.ModifyRecipesCallback,
    RecipeLoadingEvents.RemoveRecipesCallback {
    companion object {

        lateinit var registryAccess: RegistryAccess

        val DEPRECATED_ITEMS: Collection<ItemLike> = ImmutableList.of( // Wrenches
            AD_ASTRA.asItem("wrench"),
            INDREV.asItem("wrench"),
            AD_ASTRA.asItem("hammer"),
            INDREV.asItem("hammer"),  // Indrev
            INDREV.asItem("gold_plate"),
            INDREV.asItem("iron_plate"),
            INDREV.asItem("copper_plate"),
            INDREV.asItem("fan"),  // Ad Astra
            AD_ASTRA.asItem("compressed_steel"),
            AD_ASTRA.asItem("iron_plate"))
    }

    private val AD_ASTRA_MATERIALS = arrayOf("steel", "desh", "ostrum", "calorite", "iron")
    private val AD_ASTRA_DECOR_TYPES = arrayOf("pillar", "plating")

    override fun addRecipes(handler: RecipeLoadingEvents.AddRecipesCallback.RecipeHandler) {

        TechThread.THREADS.forEach { thread -> thread.addRecipes(handler) }

        CuttingRecipeTweaks.register(handler)
        OreProcessingTweaks.register(handler)
        MechAndSmithCraft.register(handler)


        // Ad Astra
        run {
            AD_ASTRA_MATERIALS.forEach { material ->
                AD_ASTRA_DECOR_TYPES.forEach { type ->
                    handler.register(
                        recipeId("stonecutting", "${material}_$type")
                    ) { id ->
                        VanillaRecipeBuilders.stonecuttingRecipe(
                            id, "",
                            Ingredient.of(TagKey.create(
                                Registries.ITEM,
                                C.id("${material}_plates")
                            )),
                            AD_ASTRA.asStack(2, "${material}_$type")
                        )
                    }
                }
            }

            val AD_ASTRA_COMPRESSED_MATERIALS = arrayOf("desh", "ostrum", "calorite")
            AD_ASTRA_COMPRESSED_MATERIALS.forEach { material ->
                handler.register(
                    recipeId("pressing", "compressed_$material")
                ) { id ->
                    PressingRecipe(
                        FreePRP(id)
                            .setIngredient(AD_ASTRA.asIngredient("${material}_ingot"))
                            .setResult(AD_ASTRA.asProcessingOutput("compressed_$material"))
                    )
                }
            }
        }

// Indrev
        run {
            val INDREV_PLATES = arrayOf("bronze", "electrum", "lead", "silver", "steel", "tin", "tungsten")
            INDREV_PLATES.forEach { plate ->
                handler.register(
                    recipeId("pressing", "${plate}_plate")
                ) { id ->
                    PressingRecipe(
                        FreePRP(id)
                            .setIngredient(INDREV.asIngredient("${plate}_ingot"))
                            .setResult(INDREV.asProcessingOutput("${plate}_plate"))
                    )
                }
            }
            /*
                        handler.register(
                            recipeId("compacting", "aquamarine_quartz")
                        ) { id ->
                            CompactingRecipe(
                                FreePRP(id)
                                    .setIngredient(INDREV.asIngredient("nikolite_dust"))
                                    .setFluidIngredient(FluidIngredient.fromFluid(FabricalityFluids.REDSTONE, FluidConstants.NUGGET))
                                    .setResult(FAB.asProcessingOutput("aquamarine_quartz"))
                                    .setHeatRequirement(HeatCondition.HEATED)
                            )
                        }

                        handler.register(
                            recipeId("sandpaper_polishing", "aquamarine_quartz")
                        ) { id ->
                            SandPaperPolishingRecipe(
                                FreePRP(id)
                                    .setIngredient(FAB.asIngredient("aquamarine_quartz"))
                                    .setResult(INDREV.asProcessingOutput("nikolite_ingot"))
                            )
                        }

             */
        }


// Dusts
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

        handler.register(
            recipeId("crushing", "diamond")
        ) { id ->
            CrushingRecipe(
                FreePRP(id)
                    .setIngredient(MC.asIngredient("diamond"))
                    .setResult(FAB.asProcessingOutput("diamond_dust"))
                    .setProcessingTime(650)
            )
        }

// Nickel compound
        handler.register(
            recipeId("filling", "nickel_compound")
        ) { id ->
            FillingRecipe(
                FreePRP(id)
                    .setIngredient(FAB.asIngredient("nickel_ingot"))
                    .setFluidIngredient(FluidIngredient.fromFluid(INDREV.asFluid("molten_iron_still"),
                        FluidConstants.NUGGET * 6))
                    .setResult(FAB.asProcessingOutput("nickel_compound"))
            )
        }

// Saws
        handler.register(
            recipeId("crafting", "stone_rod")
        ) { id ->
            VanillaRecipeBuilders.shapedRecipe("S", "S")
                .ingredient('S', Tags.Items.COBBLESTONE)
                .output(FAB.asStack("stone_rod"))
                .build(id, "")
        }

        handler.register(recipeId("crafting", "stone_saw")
        ) { id ->
            VanillaRecipeBuilders.shapedRecipe("SRR", "SMR")
                .ingredient('S', MC.asIngredient("stick"))
                .ingredient('R', FAB.asIngredient("stone_rod"))
                .ingredient('M', MC.asIngredient("flint"))
                .output(FAB.asStack("stone_saw"))
                .build(id, "")
        }

        handler.register(recipeId("crafting", "iron_saw")
        ) { id ->
            VanillaRecipeBuilders.shapedRecipe("SRR", "SMR")
                .ingredient('S', MC.asIngredient("stick"))
                .ingredient('R', FAB.asIngredient("stone_rod"))
                .ingredient('M', TagKey.create(Registries.ITEM, C.id("iron_ingots")))
                .output(FAB.asStack("iron_saw")).build(id, "")
        }

        handler.register(recipeId("crafting", "diamond_saw")
        ) { id ->
            VanillaRecipeBuilders.shapedRecipe("SRR", "SMR")
                .ingredient('S', MC.asIngredient("stick"))
                .ingredient('R', FAB.asIngredient("stone_rod"))
                .ingredient('M', MC.asIngredient("diamond"))
                .output(FAB.asStack("diamond_saw")).build(id, "")
        }
        /*
                handler.register(recipeId("smithing", "netherite_saw")
                ) { id ->
                    SmithingRecipe(id, FAB.asIngredient("diamond_saw"),
                        MC.asIngredient("netherite_ingot"), FAB.asStack("netherite_saw"))
                }

         */

        handler.register(recipeId("pressing", "zinc_sheet")
        ) { id ->
            PressingRecipe(
                FreePRP(id).setIngredient(CREATE.asIngredient("zinc_ingot"))
                    .setResult(FAB.asProcessingOutput("zinc_sheet")))
        }

// Redstone

        handler.register(
            recipeId("melting", "redstone")
        ) { id ->
            RecipeManager.fromJson(id, RecipeUtil.generateMelting(
                MC.id("redstone"),
                FAB.id("redstone"),
                FluidConstants.INGOT,
                HeatCondition.HEATED.serialize()
            ))
        }

        handler.register(recipeId("melting", "redstone_block")
        ) { id ->
            RecipeManager.fromJson(id,
                RecipeUtil.generateMelting(MC.id("redstone_block"),
                    FAB.id("redstone"), FluidConstants.BLOCK, HeatCondition.HEATED.serialize()))
        }

    }

    override fun modifyRecipes(handler: RecipeLoadingEvents.ModifyRecipesCallback.RecipeHandler) {
        TechThread.THREADS.forEach { thread -> thread.modifyRecipes(handler) }
    }

    override fun removeRecipes(handler: RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler) {
        TechThread.THREADS.forEach { thread -> thread.removeRecipes(handler) }

        OreProcessingTweaks.register(handler)
        MechAndSmithCraft.register(handler)


        // Remove wrenches except Create's and AE2's
        handler.removeIf { recipe ->
            (!CREATE.checkContains(handler, recipe) && !AE2.checkContains(handler, recipe)
                    && BuiltInRegistries.ITEM.getKey(recipe.getResultItem(registryAccess).item).path.contains("wrench"))
        }
        handler.removeIf(INDREV.predicateOutput(handler, "controller"))


        // Ad Astra!
        Arrays.stream(AD_ASTRA_MATERIALS).forEach { material ->
            Arrays.stream(AD_ASTRA_DECOR_TYPES).forEach { type ->
                handler.removeIf(AD_ASTRA.predicateOutput(handler, material.toString() + "_" + type))
            }
        }
        handler.remove(AD_ASTRA.id("recipes/nasa_workbench"))


        // AE2
        handler.removeIf(AllRecipeTypes.MILLING.getType(), AE2.predicateOutput(handler, "certus_quartz_dust").and(
            AE2.predicateIngredient(handler, "certus_quartz_crystal").negate()
        ))


        // Indrev
        handler.removeIf { recipe ->
            (INDREV.checkContains(handler, recipe)
                    && BuiltInRegistries.ITEM.getKey(recipe.getResultItem(registryAccess).item).path
                .matches(Regex(".*_(pickaxe|axe|shovel|hoe|sword)$")))
        }
        handler.removeIf(INDREV.predicateIngredient(handler, "fan"))
        handler.removeIf { recipe ->
            val resultItem = recipe.getResultItem(registryAccess).item
            resultItem is BlockItem && resultItem.block is FluidPipeBlock
        }
        handler.remove(INDREV.id("shaped/coal_generator_mk1"))
        handler.remove(INDREV.id("shaped/solar_generator_mk1"))
        handler.remove(INDREV.id("shaped/solar_generator_mk3"))
    }

    private fun recipeId(type: String, name: String): ResourceLocation {
        return Fabricality.id(type, name)
    }
}