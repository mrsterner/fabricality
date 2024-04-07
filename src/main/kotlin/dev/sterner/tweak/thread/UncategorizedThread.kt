package dev.sterner.tweak.thread

import com.simibubi.create.content.kinetics.crusher.CrushingRecipe
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe
import com.simibubi.create.content.processing.recipe.HeatCondition
import com.simibubi.create.content.processing.recipe.ProcessingOutput
import dev.sterner.Fabricality
import dev.sterner.ModCompatHelper.Entry.*
import dev.sterner.data.FreePRP
import dev.sterner.util.MechAndSmithCraft
import dev.sterner.util.MechAndSmithCraft.addEntry
import dev.sterner.util.MechAndSmithCraft.entry
import dev.sterner.util.RecipeUtil
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.AddRecipesCallback
import ho.artisan.lib.recipe.api.RecipeLoadingEvents.RemoveRecipesCallback
import ho.artisan.lib.recipe.api.builder.VanillaRecipeBuilders
import io.github.fabricators_of_create.porting_lib.tags.Tags
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.*
import org.jetbrains.annotations.Contract
import java.util.function.Function
import javax.annotation.Nullable


object UncategorizedThread : TechThread {

    lateinit var registryAccess: RegistryAccess

    val REMOVE_OUTPUTS: List<ResourceLocation> = java.util.List.of(INDREV.id("compressor_mk1"), INDREV.id("chopper_mk1"),
        INDREV.id("farmer_mk1"), INDREV.id("slaughter_mk1"), INDREV.id("rancher_mk1"), INDREV.id("pump_mk1"),
        INDREV.id("mining_rig_mk4"), INDREV.id("data_card_writer_mk4"), INDREV.id("drain_mk1"))

    override fun getLevel(): String {
        return "uncategorized"
    }

    @Contract("_, _, _ -> new")
    private fun entry(output: ResourceLocation, count: Int, @Nullable other: ResourceLocation): MechAndSmithCraft.Entry {
        return entry(this.getLevel(), INDREV.id("machine_block"), output, count, other)
    }

    override fun addRecipes(handler: AddRecipesCallback.RecipeHandler) {
        handler.register(recipeId("mechanical_crafting", "crushing_wheel")
        ) { id: ResourceLocation? ->
            RecipeUtil.mechanicalFromShaped(id, registryAccess, VanillaRecipeBuilders
                .shapedRecipe(" AAA ", "AABAA", "ABBBA", "AABAA", " AAA")
                .ingredient('A', Tags.Items.COBBLESTONE)
                .ingredient('B', Items.STICK)
                .output(CREATE.asItem("crushing_wheel").defaultInstance).build(id, ""),
                false)
        }

        handler.register(recipeId("melting", "calculation_mechanism")
        ) { id ->
            RecipeManager.fromJson(id,
                RecipeUtil.generateMelting(
                    Fabricality.id("calculation_mechanism"),
                    Fabricality.id("raw_logic"),
                    FluidConstants.NUGGET * 3,
                    HeatCondition.HEATED.serialize()))
        }

        handler.register(recipeId("campfire_cooking", "stick")
        ) { id: ResourceLocation? ->
            CampfireCookingRecipe(id, "", CookingBookCategory.MISC, MC.asIngredient("stick"), MC.asStack("torch"), 0f, 20)
        }

        handler.register(recipeId("crushing", "crushing_wheel")
        ) { id: ResourceLocation? ->
            CrushingRecipe(FreePRP(id)
                .setIngredient(CREATE.asIngredient("crushing_wheel"))
                .setResult(AE2.asProcessingOutput("singularity")).setProcessingTime(250))
        }

        handler.register(recipeId("compacting", "dye_entangled_singularity")
        ) { id: ResourceLocation? ->
            CompactingRecipe(FreePRP(id)
                .setIngredient(Ingredient.of(Tags.Items.DYES),
                    AE2.asIngredient("quantum_entangled_singularity"))
                .setResult(CREATE.asProcessingOutput("dye_entangled_singularity")))
        }

        val balls: List<Item> = listOf(AE2.asItem("red_paint_ball"), AE2.asItem("yellow_paint_ball"),
            AE2.asItem("green_paint_ball"), AE2.asItem("blue_paint_ball"),
            AE2.asItem("magenta_paint_ball"))

        handler.register(recipeId("crushing", "dye_entangled_singularity")
        ) { id: ResourceLocation? ->
            CrushingRecipe(FreePRP(id)
                .setIngredient(CREATE.asIngredient("dye_entangled_singularity"))
                .setResult(balls.stream()
                    .map<ProcessingOutput>(Function<Item, ProcessingOutput> { item: Item ->
                        ProcessingOutput(item.defaultInstance,
                            0.9f - 0.1f * balls.indexOf(item))
                    })
                    .toList())
                .setProcessingTime(50))
        }

        handler.register(recipeId("crafting", "machine_block")
        ) { id: ResourceLocation? ->
            VanillaRecipeBuilders.shapedRecipe("SSS", "SCS", "SSS")
                .ingredient('C', FAB.asItem("invar_casing"))
                .ingredient('S', FAB.asItem("inductive_mechanism"))
                .output(INDREV.asStack("machine_block")).build(id, "")
        }

        handler.register(recipeId("item_application", "invar_casing")
        ) { id: ResourceLocation? ->
            ManualApplicationRecipe(FreePRP(id)
                .setIngredient(MC.asIngredient("calcite"), FAB.asIngredient("invar_ingot"))
                .setResult(FAB.asProcessingOutput("invar_casing")))
        }
    }

    override fun load() {
        addEntry(entry(INDREV.id("electric_furnace_mk1"), 1, MC.id("furnace")))
        addEntry(entry(INDREV.id("compressor_mk1"), 1, MC.id("iron_block")))
        addEntry(entry(INDREV.id("smelter_mk4"), 1, MC.id("blast_furnace")))
        addEntry(entry(INDREV.id("pulverizer_mk1"), 1, MC.id("flint")))
        addEntry(entry(INDREV.id("sawmill_mk1"), 1, FAB.id("saw_blade")))
        addEntry(entry(INDREV.id("recycler_mk2"), 1, MC.id("composter")))
        addEntry(entry(INDREV.id("condenser_mk4"), 1, MC.id("packed_ice")))
        addEntry(entry(INDREV.id("fluid_infuser_mk1"), 1, CREATE.id("whisk")))
        addEntry(entry(INDREV.id("modular_workbench_mk4"), 1, MC.id("crafting_table")))
        addEntry(entry(INDREV.id("lazuli_flux_container_mk1"), 1, MC.id("redstone_block")))
        addEntry(entry(INDREV.id("laser_emitter_mk4"), 1, MC.id("lightning_rod")))
    }

    override fun removeRecipes(handler: RemoveRecipesCallback.RecipeHandler) {
        handler.remove(CREATE.id("mechanical_crafting", "crushing_wheel"))
        handler.removeIf(INDREV.predicateOutput(handler, false, "machine_block"))
        handler.removeIf { p -> !FAB.checkContains(handler, p) && REMOVE_OUTPUTS.stream().anyMatch { id -> id.equals(BuiltInRegistries.ITEM.getKey(p.getResultItem(registryAccess).item)) }
        }
    }
}