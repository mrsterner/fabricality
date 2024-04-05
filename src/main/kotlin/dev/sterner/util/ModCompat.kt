package dev.sterner.util

import com.simibubi.create.content.processing.recipe.ProcessingOutput
import dev.sterner.Fabricality
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import java.util.function.Predicate
import javax.annotation.Nullable


class ModCompat {
    enum class Entry(val modid: String) {
        MC("minecraft"),

        C("c"),

        CREATE("create"),

        FAB(Fabricality.MODID),

        INDREV("indrev"),

        AE2("ae2"),

        AD_ASTRA("ad_astra"),

        CC("computercraft");

        fun isLoaded(): Boolean {
            return FabricLoader.getInstance().isModLoaded(modid)
        }

        fun checkContains(@Nullable id: ResourceLocation?): Boolean {
            return id != null && id.getNamespace().equals(modid)
        }

        fun checkContains(item: Item): Boolean {
            return checkContains(BuiltInRegistries.ITEM.getKey(item))
        }

        fun checkContains(handler: RemoveRecipesCallback.RecipeHandler, recipe: Recipe<*>): Boolean {
            return handler.recipes[recipe.type]?.entries?.stream()
                ?.filter { entry -> entry.value == recipe }
                ?.map { entry -> entry.key }
                ?.findFirst()
                ?.map { key -> checkContains(key) }
                ?.orElse(false) ?: false
        }

        fun id(vararg path: String?): ResourceLocation {
            return ResourceLocation(modid, java.lang.String.join("/", *path))
        }

        fun asItem(vararg paths: String?): Item {
            return BuiltInRegistries.ITEM.get(id(*paths))
        }

        fun asItemTag(vararg paths: String?): TagKey<Item> {
            return TagKey.create(Registries.ITEM, id(*paths))
        }

        fun asStack(count: Int, vararg paths: String?): ItemStack {
            return ItemStack(asItem(*paths), count)
        }

        fun asStack(vararg paths: String?): ItemStack {
            return ItemStack(asItem(*paths), 1)
        }

        fun asIngredient(vararg paths: String?): Ingredient {
            return Ingredient.of(asItem(*paths))
        }

        fun asProcessingOutput(vararg paths: String?): ProcessingOutput {
            return asProcessingOutput(1f, *paths)
        }

        fun asProcessingOutput(chance: Float, vararg paths: String?): ProcessingOutput {
            return ProcessingOutput(asStack(*paths), chance)
        }

        fun asProcessingOutput(chance: Float, count: Int, vararg paths: String?): ProcessingOutput {
            return ProcessingOutput(asStack(count, *paths), chance)
        }

        fun asFluid(vararg paths: String?): Fluid {
            return BuiltInRegistries.FLUID.get(id(*paths))
        }

        fun asBlock(vararg paths: String?): Block {
            return BuiltInRegistries.BLOCK.get(id(*paths))
        }

        fun asSoundEvent(vararg paths: String?): SoundEvent? {
            return BuiltInRegistries.SOUND_EVENT.get(id(*paths))
        }
        fun predicateOutput(
                handler: RemoveRecipesCallback.RecipeHandler,
                containsCabf: Boolean, count: Int, vararg paths: String?
        ): Predicate<Recipe<*>> {
            return Predicate<Recipe<*>> { recipe ->
                (recipe.getResultItem(handler.registryManager).equals(asStack(count, *paths))
                        && (containsCabf || !FAB.checkContains(handler, recipe)))
            }
        }

        fun predicateOutput(
                handler: RemoveRecipesCallback.RecipeHandler,
                containsCabf: Boolean, vararg paths: String?
        ): Predicate<Recipe<*>> {
            return predicateOutput(handler, containsCabf, 1, *paths)
        }

        fun predicateOutput(
                handler: RemoveRecipesCallback.RecipeHandler,
                vararg paths: String?
        ): Predicate<Recipe<*>> {
            return predicateOutput(handler, false, *paths)
        }

        fun predicateOutput(
                handler: RemoveRecipesCallback.RecipeHandler,
                stack: ItemStack?
        ): Predicate<Recipe<*>> {
            return Predicate<Recipe<*>> { recipe -> recipe.getResultItem(handler.registryManager).equals(stack) }
        }

        fun predicateIngredient(
                handler: RemoveRecipesCallback.RecipeHandler?,
                containsCabf: Boolean, vararg paths: String?
        ): Predicate<Recipe<*>> {
            return Predicate<Recipe<*>> { recipe ->
                (recipe.ingredients.stream().anyMatch { obj: Any? ->
                    asIngredient(*paths) == obj
                }
                        && (containsCabf || !FAB.checkContains(handler!!, recipe)))
            }
        }

        fun predicateIngredient(
                handler: RemoveRecipesCallback.RecipeHandler?,
                vararg paths: String?
        ): Predicate<Recipe<*>> {
            return predicateIngredient(handler, false, *paths)
        }

        fun predicateIngredient(item: Item?): Predicate<Recipe<*>> {
            return Predicate<Recipe<*>> { recipe ->
                recipe.ingredients.stream().anyMatch(Ingredient.of(item)::equals)
            }
        }
    }
}