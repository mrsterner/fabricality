package dev.sterner.tweak.thread

import com.simibubi.create.content.fluids.transfer.FillingRecipe
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe
import com.simibubi.create.content.kinetics.mixer.MixingRecipe
import com.simibubi.create.content.processing.recipe.HeatCondition
import com.simibubi.create.foundation.fluid.FluidIngredient
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.data.FreePRP
import dev.sterner.registry.FabricalityFluids
import dev.sterner.util.ArrayUtil
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.addEntry
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.util.RecipeUtil.donutRecipe
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import me.steven.indrev.recipes.machines.FluidInfuserRecipe
import me.steven.indrev.recipes.machines.entries.InputEntry
import me.steven.indrev.recipes.machines.entries.OutputEntry
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.material.Fluids
import org.jetbrains.annotations.Contract
import javax.annotation.Nullable


object FluixThread : TechThread {

    override fun load() {
        addEntry(entry(AE2.id("condenser"), 1, AE2.id("fluix_pearl")))
        addEntry(entry(AE2.id("drive"), 1, AE2.id("engineering_processor")))
        addEntry(entry(AE2.id("formation_core"), 4, AE2.id("logic_processor")))
        addEntry(entry(AE2.id("annihilation_core"), 4, AE2.id("calculation_processor")))
        addEntry(entry(AE2.id("chest"), 1, MC.id("chest")))
    }

    override fun getLevel(): String {
        return "fluix"
    }

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        handler.register(
            recipeId("crafting", "flash_drive")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("SCA")
                .ingredient('A', Items.LAPIS_LAZULI)
                .ingredient('C', AE2.asIngredient("logic_processor"))
                .ingredient('S', MC.asIngredient("iron_ingot"))
                .output(FAB.asStack("flash_drive"))
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "controller")
        ) { id: ResourceLocation? ->
            donutRecipe(
                id, FAB.asItem("fluix_casing"),
                FAB.asItem("calculation_mechanism"),
                AE2.asItem("controller"), 1)
        }

        handler.register(
            recipeId("stonecutting", "silicon_press")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.stonecuttingRecipe(
                id, "",
                Ingredient.of(FAB.asItem("circuit_scrap")),
                AE2.asStack("silicon_press")
            )
        }

        handler.register(
            recipeId("stonecutting", "engineering_processor_press")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.stonecuttingRecipe(
                id, "",
                Ingredient.of(FAB.asItem("circuit_scrap")),
                AE2.asStack("engineering_processor_press")
            )
        }

        handler.register(
            recipeId("stonecutting", "calculation_processor_press")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.stonecuttingRecipe(
                id, "",
                Ingredient.of(FAB.asItem("circuit_scrap")),
                AE2.asStack("calculation_processor_press")
            )
        }

        handler.register(
            recipeId("stonecutting", "logic_processor_press")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.stonecuttingRecipe(
                id, "",
                Ingredient.of(FAB.asItem("circuit_scrap")),
                AE2.asStack("logic_processor_press")
            )
        }

        handler.register(recipeId("crafting", "circuit_scrap")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.shapedRecipe(" A ", "ABA", " A ")
                .ingredient('A', CREATE.asIngredient("zinc_ingot"))
                .ingredient('B', TagKey.create(Registries.ITEM, FAB.id("circuit_press")))
                .output(FAB.asStack(2, "circuit_scrap"))
                .build(id, "")
        }

        handler.register(
            recipeId("deploying", "printed_silicon")
        ) { id: ResourceLocation? ->
            DeployerApplicationRecipe(FreePRP(id)
                .setIngredient(
                    Ingredient.of(AE2.asItem("silicon")),
                    Ingredient.of(AE2.asItem("silicon_press"))
                )
                .keepHeldItem().setResult(AE2.asProcessingOutput("printed_silicon"))
            )
        }

        handler.register(
            recipeId("crushing", "blizz_cube")
        ) { id: ResourceLocation? ->
            CrushingRecipe(FreePRP(id)
                .setIngredient(FAB.asIngredient("blizz_cube"))
                .setResult(
                    FAB.asProcessingOutput(1f, 2, "blizz_powder"),
                    FAB.asProcessingOutput(0.5f, 1, "blizz_powder")
                )
                .setProcessingTime(350))
        }

        handler.register(
            recipeId("crushing", "basalz_shard")
        ) { id: ResourceLocation? ->
            CrushingRecipe(FreePRP(id)
                .setIngredient(FAB.asIngredient("basalz_shard"))
                .setResult(
                    FAB.asProcessingOutput(1f, 2, "basalz_powder"),
                    FAB.asProcessingOutput(0.5f, 1, "basalz_powder")
                )
                .setProcessingTime(350))
        }

        val blizz: Ingredient = FAB.asIngredient("blizz_powder")
        val basalz: Ingredient = FAB.asIngredient("basalz_powder")

        handler.register(
            recipeId("splashing", "sandstone")
        ) { id: ResourceLocation? ->
            SplashingRecipe(FreePRP(id)
                .setIngredient(MC.asIngredient("sandstone"))
                .setResult(FAB.asProcessingOutput(0.65f, "sand_ball")))
        }

        handler.register(
            recipeId("compacting", "ice_charge")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setIngredient(blizz, blizz, blizz, blizz, blizz, blizz, blizz, blizz)
                .setResult(FAB.asProcessingOutput("ice_charge")))
        }

        handler.register(
            recipeId("compacting", "earth_charge")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setIngredient(basalz, basalz, basalz, basalz, basalz, basalz, basalz, basalz)
                .setResult(FAB.asProcessingOutput("earth_charge")))
        }

        // Coke Processing
        /*
		handler.register(
				recipeId("compacting", "coal"),
				id -> new CompactingRecipe(new FreePRP(id)
						.setIngredient(Ingredient.ofTag(ItemTags.COALS))
						.setFluidResult(new FluidStack(CabfFluids.COKE, FluidConstants.INGOT))
						.setHeatRequirement(HeatCondition.HEATED))
		);

		 */
        handler.register(
            recipeId("filling", "coal")
        ) { id: ResourceLocation? ->
            FillingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(ItemTags.COALS))
                .setFluidIngredient(FluidIngredient.fromFluid(FabricalityFluids.COKE.get(), FluidConstants.BOTTLE))
                .setResult(FAB.asProcessingOutput("coal_coke")))
        }

        handler.register(
            recipeId("compacting", "coal_coke")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setIngredient(FAB.asIngredient("coal_coke"))
                .setFluidIngredient(FluidIngredient.fromFluid(Fluids.WATER, FluidConstants.INGOT))
                .setResult(MC.asProcessingOutput("coal")))
        }

        /*
     handler.register(
             recipeId("emptying", "sand_ball"),
             id -> new EmptyingRecipe(new FreePRP(id)
                     .setIngredient(CABF.asIngredient("sand_ball"))
                     .setResult(CABF.asProcessingOutput("rough_sand"))
                     .setFluidResult(new FluidStack(CabfFluids.FINE_SAND, FluidConstants.BUCKET / 2)))
     );

      */
        handler.register(
            recipeId("compacting", "silicon_compound")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setIngredient(
                    FAB.asIngredient("purified_sand"),
                    FAB.asIngredient("coke_chunk")
                )
                .setFluidIngredient(
                    FluidIngredient.fromFluid(FabricalityFluids.FINE_SAND.get(),
                        FluidConstants.BOTTLE)
                )
                .setResult(FAB.asProcessingOutput("silicon_compound")))
        }

        handler.register(
            recipeId("mixing", "purified_sand")
        ) { id: ResourceLocation? ->
            MixingRecipe(FreePRP(id)
                .setIngredient(
                    FAB.asIngredient("rough_sand"),
                    FAB.asIngredient("earth_charge")
                )
                .setResult(FAB.asProcessingOutput("purified_sand"))
                .setHeatRequirement(HeatCondition.HEATED))
        }

        handler.register(
            recipeId("mixing", "silicon")
        ) { id: ResourceLocation? ->
            MixingRecipe(FreePRP(id)
                .setIngredient(
                    FAB.asIngredient("silicon_compound"),
                    FAB.asIngredient("ice_charge"))
                .setResult(AE2.asProcessingOutput("silicon"))
                .setHeatRequirement(HeatCondition.HEATED))
        }

        handler.register(
            recipeId("item_application", "fluix_casing")
        ) { id: ResourceLocation? ->
            ManualApplicationRecipe(FreePRP(id)
                .setIngredient(
                    MC.asIngredient("obsidian"),
                    AE2.asIngredient("fluix_crystal")
                )
                .setResult(FAB.asProcessingOutput("fluix_casing")))
        }

        handler.register(
            recipeId("fluid_infuse", "snow")
        ) { id: ResourceLocation? ->
            FluidInfuserRecipe(
                id!!,
                ArrayUtil.of(InputEntry(MC.asIngredient("ice"), 0)),
                ArrayUtil.of(OutputEntry(MC.asStack("snowball"), 0.5)),
                ArrayUtil.of(ResourceAmount(FluidVariant.of(Fluids.WATER),
                    FluidConstants.BOTTLE)),
                ArrayUtil.of<ResourceAmount<FluidVariant>>(),
                120)
        }
    }

    @Contract("_, _, _ -> new")
    private fun entry(output: ResourceLocation,
                      count: Int,
                      @Nullable other: ResourceLocation?): MechAndSmithCraft.Entry {
        return entry(this.getLevel(), AE2.id("controller"), output, count, other)
    }

    override fun removeRecipes(handler: RemoveRecipesCallback.RecipeHandler) {
        handler.removeIf(AE2.predicateOutput(handler, false, "silicon"))
        handler.removeIf(AE2.predicateOutput(handler, false, "controller"))
    }
}