package dev.sterner.tweak.thread

import com.simibubi.create.content.fluids.transfer.FillingRecipe
import com.simibubi.create.content.kinetics.millstone.MillingRecipe
import com.simibubi.create.content.kinetics.mixer.MixingRecipe
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import com.simibubi.create.foundation.fluid.FluidIngredient
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.data.FreePRP
import dev.sterner.registry.FabricalityFluids
import dev.sterner.util.ListUtil
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.addEntry
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.util.RecipeUtil.donutRecipe
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack
import me.steven.indrev.IndustrialRevolution
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.material.Fluids
import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.Nullable


object BrassThread : TechThread {

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        handler.register(
            recipeId("milling", "sky_stone_block")
        ) { id: ResourceLocation? ->
            MillingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(AE2.asItem("sky_stone_block")))
                .setResult(ProcessingOutput(
                    AE2.asItem("sky_stone_block").defaultInstance, 1f),
                    ProcessingOutput(AE2.asItem("sky_dust").defaultInstance, 1f)
                ).setProcessingTime(350))
        }

        val redstone: Item = Items.REDSTONE
        handler.register(
            recipeId("crafting", "rose_quartz")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapelessRecipe(CREATE.asItem("rose_quartz").defaultInstance)
                .ingredient(
                    Items.QUARTZ, AE2.asItem("certus_quartz_crystal"),
                    AE2.asItem("charged_certus_quartz_crystal")
                )
                .ingredient(redstone)
                .ingredient(redstone)
                .ingredient(redstone)
                .build(id, "")
        }

        registerCrystalProcess(
            handler,
            AE2.asItem("certus_quartz_crystal"),
            AE2.asItem("certus_crystal_seed"),
            AE2.asItem("certus_quartz_dust")
        )

        registerCrystalProcess(
            handler,
            AE2.asItem("fluix_crystal"),
            AE2.asItem("fluix_crystal_seed"),
            AE2.asItem("fluix_dust")
        )


        handler.register(
            recipeId("mixing", "sky_stone")
        ) { id: ResourceLocation ->
            MixingRecipe(FreePRP(id)
                .setFluidIngredient(FluidIngredient.fromFluid(Fluids.WATER, FluidConstants.BOTTLE))
                .setIngredient(ListUtil.loop(Ingredient.of(AE2.asItem("sky_dust")), 3))
                .setFluidResult(FluidStack(FabricalityFluids.SKY_STONE.get(), FluidConstants.BOTTLE)))
        }

        /* Converted to json
		handler.register(
				recipeId("mixing", "redstone")
        ) { id: ResourceLocation? ->
            MixingRecipe(FreePRP(id)
            .setFluidIngredient(FluidIngredient.fromFluid(FabricalityFluids.SKY_STONE.get(), FluidConstants.INGOT * 2))
            .setIngredient(Ingredient.of(AE2.asItem("charged_certus_quartz_crystal")))
            .setFluidResult(FluidStack(FabricalityFluids.REDSTONE.get(), FluidConstants.INGOT * 2))
            .setResult(ProcessingOutput(AE2.asItem("certus_quartz_crystal").defaultInstance, 1f)))
        }

         */

        handler.register(
            recipeId("mixing", "polished_rose_quartz")
        ) { id: ResourceLocation? ->
            MixingRecipe(FreePRP(id)
                .setFluidIngredient(FluidIngredient.fromFluid(FabricalityFluids.REDSTONE.get(), FluidConstants.INGOT))
                .setIngredient(Ingredient.of(AE2.asItem("certus_quartz_crystal")))
                .setResult(ProcessingOutput(CREATE.asItem("polished_rose_quartz").defaultInstance, 1f)))
        }

        handler.register(
            recipeId("filling", "electron_tube")
        ) { id: ResourceLocation? ->
            IndustrialRevolution
            FillingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(CREATE.asItem("polished_rose_quartz")))
                .setFluidIngredient(FluidIngredient.fromFluid(INDREV.asFluid("molten_iron_still"),
                    FluidConstants.NUGGET))
                .setResult(ProcessingOutput(CREATE.asItem("electron_tube").defaultInstance, 1f)))
        }

        handler.register(
            recipeId("crafting", "brass_machine")
        ) { id: ResourceLocation? ->
            donutRecipe(
                id,
                CREATE.asItem("brass_casing"),
                CREATE.asItem("precision_mechanism"),
                FAB.asItem("brass_machine"),
                1
            )
        }
    }

    private fun registerCrystalProcess(handler: AddRecipesCallback.RecipeHandler,
                                       crystal: Item,
                                       seed: Item,
                                       dust: Item) {
        handler.register(recipeId("milling", BuiltInRegistries.ITEM.getKey(crystal).path)
        ) { id: ResourceLocation? ->
            MillingRecipe(
                FreePRP(id)
                    .setIngredient(Ingredient.of(crystal))
                    .setResult(ProcessingOutput(dust.defaultInstance, 1f))
                    .setProcessingTime(200)
            )
        }

        /*
        handler.register(recipeId("mechanical_crafting", Registries.ITEM.getId(seed).getPath()),
                id -> RecipeBuilderUtil.mechanicalFromShaped(
                        VanillaRecipeBuilders
                                .shapedRecipe("x")
                                .ingredient('x', crystal)
                                .output(new ItemStack(seed, 2))
                                .build(id, ""),
                        false
                )
        );

         */
    }

    override fun removeRecipes(handler: RemoveRecipesCallback.RecipeHandler) {
        handler.removeIf(AE2.predicateOutput(handler, false, "sky_dust"))
        handler.remove(CREATE.id("crafting", "materials", "electron_tube"))
        handler.remove(CREATE.id("crafting", "materials", "rose_quartz"))
        handler.remove(CREATE.id("sequenced_assembly", "precision_mechanism"))
    }

    override fun load() {
        addEntry(entry(CREATE.id("mechanical_crafter"), 3, MC.id("crafting_table")))
        addEntry(entry(CREATE.id("sequenced_gearshift"), 2, null))
        addEntry(entry(CREATE.id("rotation_speed_controller"), 1, null))
        addEntry(entry(CREATE.id("mechanical_arm"), 1, null))
        addEntry(entry(CREATE.id("stockpile_switch"), 2, null))
        addEntry(entry(CREATE.id("content_observer"), 2, null))
        addEntry(entry(INDREV.id("solid_infuser_mk1"), 1, MC.id("dropper")))
        addEntry(entry(INDREV.id("biomass_generator_mk3"), 1, INDREV.id("heat_coil")))
        addEntry(entry(CREATE.id("brass_funnel"), 4, null))
        addEntry(entry(CREATE.id("brass_tunnel"), 4, null))
        addEntry(entry(CREATE.id("elevator_pulley"), 1, MC.id("dried_kelp_block")))
    }

    @Contract("_, _, _ -> new")
    private fun entry(output: ResourceLocation,
                      count: Int,
                      @Nullable other: ResourceLocation?): MechAndSmithCraft.Entry {
        return entry(this.getLevel(), FAB.id("brass_machine"), output, count, other)
    }

    override fun getLevel(): String {
        return "brass"
    }
}