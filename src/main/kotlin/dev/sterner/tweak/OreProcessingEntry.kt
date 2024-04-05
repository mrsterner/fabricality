package dev.sterner.tweak

import dev.sterner.util.ModCompat
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
        ModCompat.Entry.MC.id("iron"),
        ModCompat.Entry.MC.id("iron_ingot"),
        ModCompat.Entry.MC.id("iron_nugget"),
        ModCompat.Entry.MC.id("raw_iron"),
        ModCompat.Entry.CREATE.id("crushed_raw_iron"),
        ModCompat.Entry.INDREV.id("iron_dust"),
        ModCompat.Entry.FAB.id("molten_iron"),
        ModCompat.Entry.MC.id("iron_ore"),
        ModCompat.Entry.MC.id("deepslate_iron_ore"),
        ModCompat.Entry.AD_ASTRA.id("moon_iron_ore"),
        ModCompat.Entry.AD_ASTRA.id("mars_iron_ore"),
        ModCompat.Entry.AD_ASTRA.id("mercury_iron_ore"),
        ModCompat.Entry.AD_ASTRA.id("glacio_iron_ore")
    ),
    GOLD(
        ModCompat.Entry.MC.id("gold"),
        ModCompat.Entry.MC.id("gold_ingot"),
        ModCompat.Entry.MC.id("gold_nugget"),
        ModCompat.Entry.MC.id("raw_gold"),
        ModCompat.Entry.CREATE.id("crushed_raw_gold"),
        ModCompat.Entry.INDREV.id("gold_dust"),
        ModCompat.Entry.FAB.id("molten_gold"),
        ModCompat.Entry.MC.id("gold_ore"),
        ModCompat.Entry.MC.id("deepslate_gold_ore"),
        ModCompat.Entry.AD_ASTRA.id("venus_gold_ore")
    ),
    COPPER(
        ModCompat.Entry.MC.id("copper"),
        ModCompat.Entry.MC.id("copper_ingot"),
        ModCompat.Entry.CREATE.id("copper_nugget"),
        ModCompat.Entry.MC.id("raw_copper"),
        ModCompat.Entry.CREATE.id("crushed_raw_copper"),
        ModCompat.Entry.INDREV.id("copper_dust"),
        ModCompat.Entry.FAB.id("molten_copper"),
        ModCompat.Entry.MC.id("copper_ore"),
        ModCompat.Entry.MC.id("deepslate_copper_ore"),
        ModCompat.Entry.AD_ASTRA.id("glacio_copper_ore")
    ),
    ZINC(
        ModCompat.Entry.CREATE.id("zinc"),
        ModCompat.Entry.CREATE.id("zinc_ingot"),
        ModCompat.Entry.CREATE.id("zinc_nugget"),
        ModCompat.Entry.CREATE.id("raw_zinc"),
        ModCompat.Entry.CREATE.id("crushed_raw_zinc"),
        ModCompat.Entry.FAB.id("zinc_dust"),
        ModCompat.Entry.FAB.id("molten_zinc"),
        ModCompat.Entry.CREATE.id("zinc_ore"),
        ModCompat.Entry.CREATE.id("deepslate_zinc_ore")
    ),
    TIN(
        ModCompat.Entry.INDREV.id("tin"),
        ModCompat.Entry.INDREV.id("tin_ingot"),
        ModCompat.Entry.INDREV.id("tin_nugget"),
        ModCompat.Entry.INDREV.id("raw_tin"),
        ModCompat.Entry.CREATE.id("crushed_raw_tin"),
        ModCompat.Entry.INDREV.id("tin_dust"),
        ModCompat.Entry.FAB.id("molten_tin"),
        ModCompat.Entry.INDREV.id("tin_ore"),
        ModCompat.Entry.INDREV.id("deepslate_tin_ore")
    ),
    LEAD(
        ModCompat.Entry.INDREV.id("lead"),
        ModCompat.Entry.INDREV.id("lead_ingot"),
        ModCompat.Entry.INDREV.id("lead_nugget"),
        ModCompat.Entry.INDREV.id("raw_lead"),
        ModCompat.Entry.CREATE.id("crushed_raw_lead"),
        ModCompat.Entry.INDREV.id("lead_dust"),
        ModCompat.Entry.FAB.id("molten_lead"),
        ModCompat.Entry.INDREV.id("lead_ore"),
        ModCompat.Entry.INDREV.id("deepslate_lead_ore")
    ),
    DESH(
        ModCompat.Entry.AD_ASTRA.id("desh"),
        ModCompat.Entry.AD_ASTRA.id("desh_ingot"),
        ModCompat.Entry.AD_ASTRA.id("desh_nugget"),
        ModCompat.Entry.AD_ASTRA.id("raw_desh"),
        ModCompat.Entry.FAB.id("crushed_raw_desh"),
        ModCompat.Entry.FAB.id("desh_dust"),
        ModCompat.Entry.FAB.id("molten_desh"),
        ModCompat.Entry.AD_ASTRA.id("moon_desh_ore"),
        ModCompat.Entry.AD_ASTRA.id("deepslate_desh_ore")
    ),
    OSTRUM(
        ModCompat.Entry.AD_ASTRA.id("ostrum"),
        ModCompat.Entry.AD_ASTRA.id("ostrum_ingot"),
        ModCompat.Entry.AD_ASTRA.id("ostrum_nugget"),
        ModCompat.Entry.AD_ASTRA.id("raw_ostrum"),
        ModCompat.Entry.FAB.id("crushed_raw_ostrum"),
        ModCompat.Entry.FAB.id("ostrum_dust"),
        ModCompat.Entry.FAB.id("molten_ostrum"),
        ModCompat.Entry.AD_ASTRA.id("mars_ostrum_ore"),
        ModCompat.Entry.AD_ASTRA.id("deepslate_ostrum_ore")
    ),
    CALORITE(
        ModCompat.Entry.AD_ASTRA.id("calorite"),
        ModCompat.Entry.AD_ASTRA.id("calorite_ingot"),
        ModCompat.Entry.AD_ASTRA.id("calorite_nugget"),
        ModCompat.Entry.AD_ASTRA.id("raw_calorite"),
        ModCompat.Entry.FAB.id("crushed_raw_calorite"),
        ModCompat.Entry.FAB.id("calorite_dust"),
        ModCompat.Entry.FAB.id("molten_calorite"),
        ModCompat.Entry.AD_ASTRA.id("venus_calorite_ore"),
        ModCompat.Entry.AD_ASTRA.id("deepslate_calorite_ore")
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
        if (!BuiltInRegistries.ITEM.containsKey(this.getIngot())) throwError(
            this.getIngot())
        if (!BuiltInRegistries.ITEM.containsKey(this.getNugget())) throwError(
            this.getNugget())
        if (!BuiltInRegistries.ITEM.containsKey(this.getRawOre())) throwError(
            this.getRawOre())
        if (!BuiltInRegistries.ITEM.containsKey(this.getCrushedOre())) throwError(
            this.getCrushedOre())
        if (!BuiltInRegistries.ITEM.containsKey(this.getDust())) throwError(
            this.getDust())
        if (!BuiltInRegistries.FLUID.containsKey(this.getMoltenMetal())) throwError(
            this.getMoltenMetal())
        for (ore in this.ores) if (!BuiltInRegistries.ITEM.containsKey(ore)) throwError(ore)
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