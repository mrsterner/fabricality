package dev.sterner.tweak

import dev.sterner.ModCompatHelper.Entry.*
import me.steven.indrev.utils.rawId
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.material.Fluid
import java.util.Arrays


enum class OreProcessingEntry(id: ResourceLocation,
                              ingot: ResourceLocation,
                              nugget: ResourceLocation,
                              raw: ResourceLocation,
                              crushed: ResourceLocation,
                              dust: ResourceLocation,
                              moltenMetal: ResourceLocation,
                              vararg ores: ResourceLocation) {
    IRON(
        MC.id("iron"),
        MC.id("iron_ingot"),
        MC.id("iron_nugget"),
        MC.id("raw_iron"),
        CREATE.id("crushed_raw_iron"),
        INDREV.id("iron_dust"),
        INDREV.id("molten_iron"),
        MC.id("iron_ore"),
        MC.id("deepslate_iron_ore"),
        AD_ASTRA.id("moon_iron_ore"),
        AD_ASTRA.id("mars_iron_ore"),
        AD_ASTRA.id("mercury_iron_ore"),
        AD_ASTRA.id("glacio_iron_ore")
    ),
    GOLD(
        MC.id("gold"),
        MC.id("gold_ingot"),
        MC.id("gold_nugget"),
        MC.id("raw_gold"),
        CREATE.id("crushed_raw_gold"),
        INDREV.id("gold_dust"),
        INDREV.id("molten_gold"),
        MC.id("gold_ore"),
        MC.id("deepslate_gold_ore"),
        AD_ASTRA.id("venus_gold_ore")
    ),
    COPPER(
        MC.id("copper"),
        MC.id("copper_ingot"),
        CREATE.id("copper_nugget"),
        MC.id("raw_copper"),
        CREATE.id("crushed_raw_copper"),
        INDREV.id("copper_dust"),
        INDREV.id("molten_copper"),
        MC.id("copper_ore"),
        MC.id("deepslate_copper_ore"),
        AD_ASTRA.id("glacio_copper_ore")
    ),
    ZINC(
        CREATE.id("zinc"),
        CREATE.id("zinc_ingot"),
        CREATE.id("zinc_nugget"),
        CREATE.id("raw_zinc"),
        CREATE.id("crushed_raw_zinc"),
        FAB.id("zinc_dust"),
        FAB.id("molten_zinc"),
        CREATE.id("zinc_ore"),
        CREATE.id("deepslate_zinc_ore")
    ),
    TIN(
        INDREV.id("tin"),
        INDREV.id("tin_ingot"),
        INDREV.id("tin_nugget"),
        INDREV.id("raw_tin"),
        CREATE.id("crushed_raw_tin"),
        INDREV.id("tin_dust"),
        INDREV.id("molten_tin"),
        INDREV.id("tin_ore"),
        INDREV.id("deepslate_tin_ore")
    ),
    LEAD(
        INDREV.id("lead"),
        INDREV.id("lead_ingot"),
        INDREV.id("lead_nugget"),
        INDREV.id("raw_lead"),
        CREATE.id("crushed_raw_lead"),
        INDREV.id("lead_dust"),
        INDREV.id("molten_lead"),
        INDREV.id("lead_ore"),
        INDREV.id("deepslate_lead_ore")
    ),
    DESH(
        AD_ASTRA.id("desh"),
        AD_ASTRA.id("desh_ingot"),
        AD_ASTRA.id("desh_nugget"),
        AD_ASTRA.id("raw_desh"),
        FAB.id("crushed_raw_desh"),
        FAB.id("desh_dust"),
        FAB.id("molten_desh"),
        AD_ASTRA.id("moon_desh_ore"),
        AD_ASTRA.id("deepslate_desh_ore")
    ),
    OSTRUM(
        AD_ASTRA.id("ostrum"),
        AD_ASTRA.id("ostrum_ingot"),
        AD_ASTRA.id("ostrum_nugget"),
        AD_ASTRA.id("raw_ostrum"),
        FAB.id("crushed_raw_ostrum"),
        FAB.id("ostrum_dust"),
        FAB.id("molten_ostrum"),
        AD_ASTRA.id("mars_ostrum_ore"),
        AD_ASTRA.id("deepslate_ostrum_ore")
    ),
    CALORITE(
        AD_ASTRA.id("calorite"),
        AD_ASTRA.id("calorite_ingot"),
        AD_ASTRA.id("calorite_nugget"),
        AD_ASTRA.id("raw_calorite"),
        FAB.id("crushed_raw_calorite"),
        FAB.id("calorite_dust"),
        FAB.id("molten_calorite"),
        AD_ASTRA.id("venus_calorite_ore"),
        AD_ASTRA.id("deepslate_calorite_ore")
    );

    private val id: ResourceLocation = id
    private val ingot: ResourceLocation = ingot
    private val nugget: ResourceLocation = nugget
    private val rawOre: ResourceLocation = raw
    private val crushedOre: ResourceLocation = crushed
    private val dust: ResourceLocation = dust
    private val moltenMetal: ResourceLocation = moltenMetal
    private val ores: List<ResourceLocation> = mutableListOf(*ores)

    fun getId(): ResourceLocation {
        return id
    }

    fun getIngot(): ResourceLocation {
        return ingot
    }

    fun getNugget(): ResourceLocation {
        return nugget
    }

    fun getRawOre(): ResourceLocation {
        return rawOre
    }

    fun getCrushedOre(): ResourceLocation {
        return crushedOre
    }

    fun getDust(): ResourceLocation {
        return dust
    }

    fun getMoltenMetal(): ResourceLocation {
        return moltenMetal
    }

    fun getOres(): List<ResourceLocation> {
        return ores
    }

    val ingotItem: Item
        get() = BuiltInRegistries.ITEM.get(this.getIngot())

    val nuggetItem: Item
        get() = BuiltInRegistries.ITEM.get(this.getNugget())

    val rawOreItem: Item
        get() = BuiltInRegistries.ITEM.get(this.getRawOre())

    val crushedOreItem: Item
        get() = BuiltInRegistries.ITEM.get(this.getCrushedOre())

    val dustItem: Item
        get() = BuiltInRegistries.ITEM.get(this.getDust())

    val moltenMetalFluid: Fluid
        get() = BuiltInRegistries.FLUID.get(this.getMoltenMetal())

    val oreItems: List<Any>
        get() {
            val items: ArrayList<Item> = ArrayList<Item>()
            for (ore in this.getOres()) items.add(BuiltInRegistries.ITEM.get(ore))
            return items
        }

    fun isTargetOre(item: Item?): Boolean {
        val id: String = BuiltInRegistries.ITEM.getKey(item).getPath()
        return id.contains(getId().getPath()) && id.contains("ore")
    }

    fun check() {
        if (!BuiltInRegistries.ITEM.containsKey(this.getIngot())) {
            throwError(this.getIngot())
        }
        if (!BuiltInRegistries.ITEM.containsKey(this.getNugget())) {
            throwError(this.getNugget())
        }
        if (!BuiltInRegistries.ITEM.containsKey(this.getRawOre())) {
            throwError(this.getRawOre())
        }
        if (!BuiltInRegistries.ITEM.containsKey(this.getCrushedOre())) {
            throwError(this.getCrushedOre())
        }
        if (!BuiltInRegistries.ITEM.containsKey(this.getDust())) {
            throwError(this.getDust())
        }
        if (!BuiltInRegistries.FLUID.containsKey(this.getMoltenMetal()) && !BuiltInRegistries.BLOCK.containsKey(this.getMoltenMetal())) {
            throwError(this.getMoltenMetal())
        }
        for (ore in this.ores) {
            if (!BuiltInRegistries.ITEM.containsKey(ore)) {
                throwError(ore)
            }
        }
    }

    val hashCode: Int
        get() = getId().hashCode()

    companion object {
        fun get(id: ResourceLocation?): OreProcessingEntry {
            return Arrays.stream(entries.toTypedArray()).filter { entry -> entry.getId().equals(id) }
                .findFirst().orElse(null)
        }

        private fun throwError(id: ResourceLocation) {
            throw RuntimeException("OreProcessingEntry requires $id which is not valid!")
        }

        fun checkAll() {
            Arrays.stream(entries.toTypedArray()).forEach { obj: OreProcessingEntry -> obj.check() }
        }
    }
}