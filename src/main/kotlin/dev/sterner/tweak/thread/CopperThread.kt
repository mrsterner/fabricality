package dev.sterner.tweak.thread

import com.simibubi.create.content.kinetics.mixer.CompactingRecipe
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import com.simibubi.create.foundation.fluid.FluidIngredient
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.data.FreePRP
import dev.sterner.registry.FabricalityFluids
import dev.sterner.registry.FabricalityItems
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.addEntry
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.util.RecipeUtil.donutRecipe
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.material.Fluids
import org.jetbrains.annotations.Contract
import javax.annotation.Nullable


object CopperThread : TechThread {

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        handler.register(
            recipeId("crafting", "belt_connector")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("XXX", "XXX")
                .ingredient('X', FabricalityItems.CURED_RUBBER)
                .output(ItemStack(CREATE.asItem("belt_connector"), 3))
                .build(id, "")
        }

        handler.register(
            recipeId("smelting", "cured_rubber")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.smeltingRecipe(
                id, "",
                Ingredient.of(FabricalityItems.RUBBER),
                CookingBookCategory.MISC,
                FabricalityItems.CURED_RUBBER.defaultInstance,
                0.1f, 120
            )
        }

        handler.register(
            recipeId("crafting", "sealed_mechanism")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders
                .shapedRecipe("XOX")
                .ingredient('X', Ingredient.of(FabricalityItems.CURED_RUBBER))
                .ingredient('O',
                    Ingredient.of(FAB.asItem("kinetic_mechanism")))
                .output(FAB.asItem("sealed_mechanism").defaultInstance)
                .build(id, "")
        }

        handler.register(
            recipeId("crafting", "copper_machine")
        ) { id: ResourceLocation? ->
            donutRecipe(
                id,
                CREATE.asItem("copper_casing"),
                FAB.asItem("sealed_mechanism"),
                FAB.asItem("copper_machine"),
                1
            )
        }

        handler.register(
            recipeId("compacting", "rubber")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setFluidIngredient(FluidIngredient.fromFluid(FabricalityFluids.RESIN.get(), FluidConstants.BOTTLE))
                .setResult(ProcessingOutput(FabricalityItems.RUBBER.defaultInstance, 1f)))
        }

        handler.register(
            recipeId("compacting", "rubber_from_flower")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setFluidIngredient(FluidIngredient.fromFluid(Fluids.WATER, FluidConstants.BOTTLE))
                .setIngredient(
                    Ingredient.of(ItemTags.FLOWERS),
                    Ingredient.of(ItemTags.FLOWERS),
                    Ingredient.of(ItemTags.FLOWERS),
                    Ingredient.of(ItemTags.FLOWERS)
                )
                .setResult(ProcessingOutput(FabricalityItems.RUBBER.defaultInstance, 1f)))
        }

        handler.register(
            recipeId("compacting", "rubber_from_vine")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setFluidIngredient(FluidIngredient.fromFluid(Fluids.WATER, FluidConstants.BOTTLE))
                .setIngredient(
                    Ingredient.of(Items.VINE),
                    Ingredient.of(Items.VINE),
                    Ingredient.of(Items.VINE),
                    Ingredient.of(Items.VINE)
                )
                .setResult(ProcessingOutput(FabricalityItems.RUBBER.defaultInstance, 1f)))
        }
    }

    override fun removeRecipes(handler: RemoveRecipesCallback.RecipeHandler) {
        handler.remove(CREATE.id("crafting", "kinetics", "belt_connector"))
    }

    override fun load() {
        addEntry(entry(CREATE.id("copper_backtank"), 1, MC.id("copper_block")))
        addEntry(entry(CREATE.id("portable_fluid_interface"), 2, null))
        addEntry(entry(INDREV.id("tier_upgrade_mk2"), 1, MC.id("redstone")))
        addEntry(entry(CREATE.id("hose_pulley"), 1, null))
        addEntry(entry(CREATE.id("item_drain"), 1, MC.id("iron_bars")))
        addEntry(entry(INDREV.id("heat_generator_mk4"), 1, INDREV.id("heat_coil")))
        addEntry(entry(INDREV.id("fisher_mk2"), 1, MC.id("bucket")))
        addEntry(entry(CREATE.id("steam_engine"), 2, MC.id("piston")))
        addEntry(entry(CREATE.id("smart_fluid_pipe"), 2, CREATE.id("electron_tube")))
    }

    override fun getLevel(): String {
        return "copper"
    }

    @Contract("_, _, _ -> new")
    private fun entry(output: ResourceLocation,
                      count: Int,
                      @Nullable other: ResourceLocation?): MechAndSmithCraft.Entry {
        return entry(this.getLevel(), FAB.id("copper_machine"), output, count, other)
    }
}