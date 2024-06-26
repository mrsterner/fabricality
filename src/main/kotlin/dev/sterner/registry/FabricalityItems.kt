package dev.sterner.registry

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem
import dev.sterner.Fabricality
import dev.sterner.data.ItemModelGenerator
import dev.sterner.item.GlintedItem
import dev.sterner.item.MechanismItem
import dev.sterner.item.SawItem
import dev.sterner.item.ToolMaterialIndex
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import pers.solid.brrp.v1.model.ModelJsonBuilder
import java.util.*
import java.util.function.Consumer


object FabricalityItems {

    private val ITEM_GROUPING_MAP = mutableSetOf<Item>()

    val ALGAL_BLEND: Item = registerItemModeled("algal_blend",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "algal_blend"))

    val ALGAL_BRICK: Item = registerItemModeled("algal_brick",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "algal_brick"))

    val MATTER_PLASTICS: Item = registerItemModeled("matter_plastics",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "matter_plastics"))

    val STONE_ROD: Item =
        registerItemModeled("stone_rod", Item(FabricItemSettings()), ItemModelGenerator.generated("item", "stone_rod"))


    val SAW_BLADE: Item = registerItemModeled("saw_blade",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "saw_blade")
    )

    val RUBBER: Item =
        registerItemModeled("rubber", Item(FabricItemSettings()), ItemModelGenerator.generated("item", "rubber"))

    val CURED_RUBBER: Item = registerItemModeled("cured_rubber",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "cured_rubber"))

    val FLASH_DRIVE: Item = registerItemModeled("flash_drive",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "boot_medium"))

    val CIRCUIT_SCRAP: Item = registerItemModeled("circuit_scrap",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "circuit_scrap"))

    val COAL_COKE: Item =
        registerItemModeled("coal_coke", Item(FabricItemSettings()), ItemModelGenerator.generated("item", "coal_coke"))
    val COKE_CHUNK: Item = registerItemModeled("coke_chunk",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "coke_chunk"))
    val INCOMPLETE_COKE_CHUNK: Item = registerItemModeled("incomplete_coke_chunk",
        SequencedAssemblyItem(FabricItemSettings()),
        ItemModelGenerator.generated("item", "incomplete_coke_chunk"))

    val ZINC_SHEET: Item = registerItemModeled("zinc_sheet",
        Item(FabricItemSettings()), ItemModelGenerator.generated("item", "zinc_sheet"))

    val SILICON_COMPOUND: Item = registerItemModeled("silicon_compound",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "silicon_compound"))

    val SAND_BALL: Item = registerItemModeled("sand_ball",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "sand_ball"))

    val ROUGH_SAND: Item = registerItemModeled("rough_sand",
        Item(FabricItemSettings()), ItemModelGenerator.generated("item", "rough_sand"))

    val PURIFIED_SAND: Item = registerItemModeled("purified_sand",
        Item(FabricItemSettings()),
        ItemModelGenerator.generated("item", "purified_sand"))

    val COMPUTATION_MATRIX: Item = registerItem("computation_matrix",
        GlintedItem(FabricItemSettings()))

    val CRUSHED_RAW_MATERIALS: List<String> = listOf("desh", "ostrum", "calorite", "cobalt")
    val DUSTS: List<String> = listOf("zinc", "desh", "ostrum", "calorite", "cobalt", "diamond", "emerald", "nickel")
    val PROCESSORS: List<String> = listOf("calculation", "logic", "engineering")

    private fun registerItemModeled(name: String, item: Item, model: ModelJsonBuilder): Item {
        Fabricality.RRPs.CLIENT_RESOURCES.addModel(Fabricality.id("item", name), model)
        return registerItem(name, item)
    }


    private fun registerItem(name: String, item: Item): Item {
        ITEM_GROUPING_MAP.add(item)
        return Registry.register(BuiltInRegistries.ITEM, Fabricality.id(name), item)
    }

    fun registerItemGroupingEvent() {
        ItemGroupEvents.modifyEntriesEvent(Fabricality.GENERAL_KEY).register { content ->
            ITEM_GROUPING_MAP.forEach { item ->
                content.accept(item)
            }
        }
    }

    fun register() {

        Arrays.stream(MechanismItem.Type.values()).forEach { type ->
            registerItemModeled(
                type.itemId.path,
                type.item,
                ItemModelGenerator.generated(type.item.textureId.path)
            )
            registerItemModeled(
                type.incompleteItemId.path,
                type.incompleteItem,
                ItemModelGenerator.generated(type.item.incompleteTextureId.path)
            )
        }


        // Saws
        Arrays.stream(ToolMaterialIndex.values()).forEach { materialIndex ->
            val itemId: String = materialIndex.typeName + "_saw"
            registerItemModeled(
                itemId,
                SawItem(materialIndex.getMaterial(), FabricItemSettings()),
                ItemModelGenerator.generated("item", "tool", "saw", itemId)
            )
        }

        // Dusts
        DUSTS.forEach(Consumer { variant: String ->
            val itemId = variant + "_dust"
            registerItemModeled(
                itemId,
                Item(FabricItemSettings()),
                ItemModelGenerator.generated("item", "dust", itemId),
            )
        })

        // Crushed Ores
        CRUSHED_RAW_MATERIALS.forEach(Consumer { variant: String ->
            val itemId = "crushed_raw_$variant"
            registerItemModeled(
                itemId,
                Item(FabricItemSettings()),
                ItemModelGenerator.generated("item", "crushed_raw_material", itemId),
            )
        })

        // Incomplete Processors
        PROCESSORS.forEach(Consumer { type: String ->
            registerItemModeled(
                "incomplete_" + type + "_processor",
                SequencedAssemblyItem(FabricItemSettings()),
                ItemModelGenerator.generated("item/processor", "incomplete_" + type + "_processor")
            )
        })

        registerItemGroupingEvent()
    }
}