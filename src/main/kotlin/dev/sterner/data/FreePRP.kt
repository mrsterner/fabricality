package dev.sterner.data

import com.simibubi.create.content.processing.recipe.HeatCondition
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder
import com.simibubi.create.foundation.fluid.FluidIngredient
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Ingredient


class FreePRP(id: ResourceLocation?) : ProcessingRecipeBuilder.ProcessingRecipeParams(id) {
    fun setIngredient(ingredients: List<Ingredient?>?): FreePRP {
        this.ingredients.addAll(ingredients!!)
        return this
    }

    fun setIngredient(vararg ingredients: Ingredient?): FreePRP {
        return this.setIngredient(listOf(*ingredients))
    }

    fun setResult(results: List<ProcessingOutput?>?): FreePRP {
        this.results.addAll(results!!)
        return this
    }

    fun setResult(vararg results: ProcessingOutput?): FreePRP {
        return this.setResult(listOf(*results))
    }

    fun setFluidIngredient(ingredients: List<FluidIngredient>): FreePRP {
        fluidIngredients.addAll(ingredients)
        return this
    }

    fun setFluidIngredient(vararg ingredients: FluidIngredient): FreePRP {
        return setFluidIngredient(ingredients.toList())
    }

    fun setFluidResult(results: List<FluidStack?>?): FreePRP {
        // this.fluidResults.addAll(results);
        return this
    }

    fun setFluidResult(vararg results: FluidStack?): FreePRP {
        return this.setFluidResult(listOf(*results))
    }

    fun setProcessingTime(processingTime: Int): FreePRP {
        this.processingDuration = processingTime
        return this
    }

    fun setHeatRequirement(condition: HeatCondition?): FreePRP {
        this.requiredHeat = condition
        return this
    }

    fun keepHeldItem(bool: Boolean?): FreePRP {
        this.keepHeldItem = bool!!
        return this
    }

    fun keepHeldItem(): FreePRP {
        return this.keepHeldItem(true)
    }
}