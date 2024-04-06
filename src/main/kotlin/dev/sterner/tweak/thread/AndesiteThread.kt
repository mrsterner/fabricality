package dev.sterner.tweak.thread

import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe
import com.simibubi.create.content.kinetics.mixer.MixingRecipe
import com.simibubi.create.content.kinetics.saw.CuttingRecipe
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder
import com.simibubi.create.foundation.fluid.FluidIngredient
import dev.sterner.data.FreePRP
import dev.sterner.item.FabItemTags
import dev.sterner.registry.FabricalityItems
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.util.RecipeUtil
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.material.Fluids
import javax.annotation.Nullable


object AndesiteThread : TechThread {
    override fun getLevel(): String {
       return "andesite"
    }

    private fun entry(output: ResourceLocation, count: Int, @Nullable other: ResourceLocation?): MechAndSmithCraft.Entry {
        return entry(getLevel(), FAB.id("andesite_machine"), output, count, other)
    }

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        handler.register(
            recipeId("smelting", "algal_blend")

        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.smeltingRecipe(
                id, "",
                Ingredient.of(FabricalityItems.ALGAL_BLEND),
                CookingBookCategory.MISC,
                FabricalityItems.ALGAL_BRICK.defaultInstance,
                0f, 120)
        }

        handler.register(
            recipeId("crafting", "algal_blend")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("SS", "AA")
                .ingredient('A', Items.CLAY_BALL)
                .ingredient('S', Items.KELP, Items.SEAGRASS)
                .output(FAB.asStack(2, "algal_blend"))
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "algal_blend_2")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("AA", "SS")
                .ingredient('A', Items.CLAY_BALL)
                .ingredient('S', Items.KELP, Items.SEAGRASS)
                .output(FAB.asStack(2, "algal_blend"))
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "andesite_alloy")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("SS", "AA")
                .ingredient('A', Items.ANDESITE)
                .ingredient('S', FabricalityItems.ALGAL_BLEND)
                .output(CREATE.asStack(2, "andesite_alloy"))
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "andesite_alloy_2")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("AA", "SS")
                .ingredient('A', Items.ANDESITE)
                .ingredient('S', FAB.asItem("algal_brick"))
                .output(CREATE.asStack(2, "andesite_alloy"))
                .build(id, "")
        }

        handler.register(
            recipeId("mixing", "algal_blend")
        ) { id: ResourceLocation? ->
            MixingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(Items.CLAY_BALL),
                    Ingredient.of(Items.KELP, Items.SEAGRASS))
                .setResult(ProcessingOutput(ItemStack(FAB.asItem("algal_blend")), 2f))
            )
        }

        handler.register(
            recipeId("mixing", "andesite_alloy")
        ) { id: ResourceLocation? ->
            MixingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(FAB.asItem("algal_brick")),
                    Ingredient.of(Items.ANDESITE))
                .setResult(ProcessingOutput(ItemStack(CREATE.asItem("andesite_alloy")), 2f))
            )
        }

        handler.register(
            recipeId("crafting", "kinetic_mechanism")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapelessRecipe(FAB.asItem("kinetic_mechanism").defaultInstance)
                .ingredient(CREATE.asItem("cogwheel"))
                .ingredient(CREATE.asItem("andesite_alloy"))
                .ingredient(ItemTags.LOGS).ingredient(FabItemTags.SAWS)
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "andesite_machine")
        ) { id: ResourceLocation? ->
            RecipeUtil.donutRecipe(
                id,
                CREATE.asItem("andesite_casing"),
                FAB.asItem("kinetic_mechanism"),
                FAB.asItem("andesite_machine"),
                1
            )
        }

        handler.register(
            recipeId("compacting", "dripstone_block")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(CREATE.asItem("limestone")))
                .setFluidIngredient(FluidIngredient.fromFluid(Fluids.WATER, FluidConstants.BOTTLE * 2))
                .setResult(ProcessingOutput(MC.asItem("dripstone_block").defaultInstance, 1f)))
        }

        handler.register(
            recipeId("sequenced_assembly", "kinetic_mechanism")
        ) { id: ResourceLocation? ->
            SequencedAssemblyRecipeBuilder(
                id)
                .require(ItemTags.WOODEN_SLABS)
                .transitionTo(FAB.asItem("incomplete_kinetic_mechanism"))
                .addOutput(FAB.asItem("kinetic_mechanism"), 1.0f).loops(1)
                .addStep<DeployerApplicationRecipe>({ params: ProcessingRecipeParams? ->
                    DeployerApplicationRecipe(params)
                },
                    { r: ProcessingRecipeBuilder<DeployerApplicationRecipe?> ->
                        r.require(CREATE.asItem("andesite_alloy"))
                    })
                .addStep<DeployerApplicationRecipe>({ params: ProcessingRecipeParams? ->
                    DeployerApplicationRecipe(params)
                },
                    { r: ProcessingRecipeBuilder<DeployerApplicationRecipe?> ->
                        r.require(CREATE.asItem("andesite_alloy"))
                    })
                .addStep<CuttingRecipe>({ params: ProcessingRecipeParams? ->
                    CuttingRecipe(params)
                },
                    { r: ProcessingRecipeBuilder<CuttingRecipe?>? -> r })
                .build()
        }

        handler.register(
            recipeId("crafting", "saw_blade")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("NPN", "PLP", "NPN")
                .ingredient('N', MC.asIngredient("iron_nugget"))
                .ingredient('P', CREATE.asIngredient("iron_sheet"))
                .ingredient('L', INDREV.asIngredient("lead_ingot"))
                .output(FAB.asStack("saw_blade"))
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "iron_drill_head")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("NN ", "NLP", " PL")
                .ingredient('N', MC.asIngredient("iron_nugget"))
                .ingredient('P', CREATE.asIngredient("iron_sheet"))
                .ingredient('L', INDREV.asIngredient("lead_ingot"))
                .output(INDREV.asStack("iron_drill_head"))
                .build(id, "")
        }
    }
    override fun removeRecipes(handler: RemoveRecipesCallback.RecipeHandler) {
        handler.remove(CREATE.id("crafting", "materials", "andesite_alloy"))
        handler.remove(CREATE.id("crafting", "materials", "andesite_alloy_from_zinc"))
        handler.remove(CREATE.id("mixing", "andesite_alloy"))
        handler.remove(CREATE.id("mixing", "andesite_alloy_from_zinc"))

        handler.removeIf { p: Recipe<*>? ->
            (p is AbstractCookingRecipe
                    && FAB.predicateOutput(handler, false, "algal_brick").test(p))
        }
        handler.removeIf(INDREV.predicateOutput(handler, false, "iron_drill_head"))
        handler.remove(FAB.id("algal_blend_shapeless"))
    }

    override fun load() {
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_press"), 1, MC.id("iron_block")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_mixer"), 1, CREATE.id("whisk")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("encased_fan"), 1, INDREV.id("fan")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_drill"), 1, INDREV.id("iron_drill_head")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_saw"), 1, FAB.id("saw_blade")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("deployer"), 1, CREATE.id("brass_hand")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("andesite_tunnel"), 4, null))
        MechAndSmithCraft.addEntry(entry(CREATE.id("andesite_funnel"), 4, null))
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_harvester"), 2, null))
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_plough"), 2, null))
        MechAndSmithCraft.addEntry(entry(CREATE.id("portable_storage_interface"), 2, null))
        MechAndSmithCraft.addEntry(entry(FAB.id("extractor_machine"), 1, MC.id("bucket")))
        MechAndSmithCraft.addEntry(entry(AD_ASTRA.id("coal_generator"), 1, INDREV.id("heat_coil")))
        MechAndSmithCraft.addEntry(entry(AE2.id("charger"), 1, AE2.id("fluix_crystal")))
        MechAndSmithCraft.addEntry(entry(CREATE.id("contraption_controls"), 2, null))
        MechAndSmithCraft.addEntry(entry(CREATE.id("mechanical_roller"), 1, CREATE.id("crushing_wheel")))
    }
}