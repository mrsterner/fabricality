package dev.sterner.tweak.thread

import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.data.FreePRP
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.addEntry
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.util.RecipeUtil.donutRecipe
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.crafting.Ingredient
import org.jetbrains.annotations.Contract
import javax.annotation.Nullable


object ZincThread : TechThread {
    override fun getLevel(): String {
        return "zinc"
    }

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        /*
		handler.register(
				recipeId("mixing", "liquid_soul"),
				id -> new MixingRecipe(new FreePRP(id)
						.setIngredient(
								Ingredient.ofItems(Items.WEEPING_VINES),
								Ingredient.ofItems(Items.TWISTING_VINES)
						)
						.setFluidResult(new FluidStack(TC.asFluid("liquid_soul"), FluidConstants.BOTTLE))
						.setHeatRequirement(HeatCondition.HEATED))
		);

		 */

        handler.register(
            recipeId("crafting", "zinc_machine")
        ) { id: ResourceLocation? ->
            donutRecipe(
                id,
                FAB.asItem("zinc_casing"),
                FAB.asItem("infernal_mechanism"),
                FAB.asItem("zinc_machine"),
                1
            )
        }

        handler.register(
            recipeId("item_application", "zinc_casing")
        ) { id: ResourceLocation? ->
            ManualApplicationRecipe(FreePRP(id)
                .setIngredient(
                    Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS),
                    FAB.asIngredient("zinc_sheet")
                )
                .setResult(FAB.asProcessingOutput("zinc_casing")))
        }
    }

    override fun load() {
        addEntry(entry(AD_ASTRA.id("solar_panel"), 1, MC.id("glass")))
        addEntry(entry(INDREV.id("tier_upgrade_mk3"), 1, MC.id("redstone")))
    }

    @Contract("_, _, _ -> new")
    private fun entry(output: ResourceLocation,
                      count: Int,
                      @Nullable other: ResourceLocation): MechAndSmithCraft.Entry {
        return entry(this.getLevel(), FAB.id("zinc_machine"), output, count, other)
    }
}