package dev.sterner.tweak

import dev.sterner.util.ModCompat.Entry.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import javax.annotation.Nullable


enum class WoodCuttingEntry(id: ResourceLocation,
                            plank: String?, plankSlab: String?,
                            log: String?, strippedLog: String?,
                            wood: String?, strippedWood: String?
) {
    OAK(
        MC.id("oak"),
        "oak_planks", "oak_slab",
        null, null,
        null, null
    ),

    SPRUCE(
        MC.id("spruce"),
        "spruce_planks", "spruce_slab",
        null, null,
        null, null
    ),

    BIRCH(
        MC.id("birch"),
        "birch_planks", "birch_slab",
        null, null,
        null, null
    ),

    JUNGLE(
        MC.id("jungle"),
        "jungle_planks", "jungle_slab",
        null, null,
        null, null
    ),

    ACACIA(
        MC.id("acacia"),
        "acacia_planks", "acacia_slab",
        null, null,
        null, null
    ),

    DARK_OAK(
        MC.id("dark_oak"),
        "dark_oak_planks", "dark_oak_slab",
        null, null,
        null, null
    ),

    CRIMSON(
        MC.id("crimson"),
        "crimson_planks", "crimson_slab",
        null, null,
        null, null
    ),

    WARPED(
        MC.id("warped"),
        "warped_planks", "warped_slab",
        null, null,
        null, null
    ),
/*
    CHERRY_OAK(
        PROMENADE.id("cherry_oak"),
        "cherry_oak_planks", "cherry_oak_slab",
        "cherry_oak_log", "stripped_cherry_oak_log",
        "cherry_oak_wood", "stripped_cherry_oak_wood"
    ),

    PALM(
        PROMENADE.id("palm"),
        "palm_planks", "palm_slab",
        "palm_log", "stripped_palm_log",
        "palm_wood", "stripped_palm_wood"
    ),  /*

 */
	RUNEWOOD(
			MLM.id("runewood"),
			"runewood_planks", "runewood_planks_slab",
			"runewood_log", "stripped_runewood_log",
			"runewood", "stripped_runewood"
	),

	SOULWOOD(
			MLM.id("soulwood"),
			"soulwood_planks", "soulwood_planks_slab",
			"soulwood_log", "stripped_soulwood_log",
			"soulwood", "stripped_soulwood"
	),


    BLOODSHROOM(
        TC.id("bloodshroom"),
        "bloodshroom_planks", "bloodshroom_planks_slab",
        null, null,
        null, null
    ),

    SKYROOT(
        TC.id("skyroot"),
        "skyroot_planks", "skyroot_planks_slab",
        null, null,
        null, null
    ),

    GREENHEART(
        TC.id("greenheart"),
        "greenheart_planks", "greenheart_planks_slab",
        null, null,
        null, null
    ),
*/
    GLACIAN(
        AD_ASTRA.id("glacian"),
        "glacian_planks", "glacian_slab",
        "glacian_log", "stripped_glacian_log",
        null, null
    );



    /*
    TWISTED(
        AP.id("twisted"),
        "twisted_planks", "twisted_slab",
        "twisted_log", "stripped_twisted_log",
        "twisted_wood", "stripped_twisted_wood"
    ),

    RAINBOW_EUCALYPTUS(
        TERRESTRIA.id("rainbow_eucalyptus"),
        "rainbow_eucalyptus_planks", "rainbow_eucalyptus_slab",
        "rainbow_eucalyptus_log", "stripped_rainbow_eucalyptus_log",
        "rainbow_eucalyptus_wood", "stripped_rainbow_eucalyptus_wood"
    ),

    RAINBOW_EUCALYPTUS_QUARTER(
        TERRESTRIA.id("rainbow_eucalyptus_quarter"),
        "rainbow_eucalyptus_planks", null,
        "rainbow_eucalyptus_quarter_log", "stripped_rainbow_eucalyptus_quarter_log",
        null, null
    ),

    CYPRESS(
        TERRESTRIA.id("cypress"),
        "cypress_planks", "cypress_slab",
        "cypress_log", "stripped_cypress_log",
        "cypress_wood", "stripped_cypress_wood"
    ),

    CYPRESS_QUARTER(
        TERRESTRIA.id("cypress_quarter"),
        "cypress_planks", "cypress_planks",
        "cypress_quarter_log", "stripped_cypress_quarter_log",
        null, null
    ),

    HEMLOCK(
        TERRESTRIA.id("hemlock"),
        "hemlock_planks", "hemlock_slab",
        "hemlock_log", "stripped_hemlock_log",
        "hemlock_wood", "stripped_hemlock_wood"
    ),

    HEMLOCK_QUARTER(
        TERRESTRIA.id("hemlock_quarter"),
        "hemlock_planks", null,
        "hemlock_quarter_log", "stripped_hemlock_quarter_log",
        null, null
    ),

    REDWOOD(
        TERRESTRIA.id("redwood"),
        "redwood_planks", "redwood_slab",
        "redwood_log", "stripped_redwood_log",
        "redwood_wood", "stripped_redwood_wood"
    ),

    REDWOOD_QUARTER(
        TERRESTRIA.id("redwood_quarter"),
        "redwood_planks", null,
        "redwood_quarter_log", "stripped_redwood_quarter_log",
        null, null
    ),

    JAPANESE_MAPLE(
        TERRESTRIA.id("japanese_maple"),
        "japanese_maple_planks", "japanese_maple_slab",
        "japanese_maple_log", "stripped_japanese_maple_log",
        "japanese_maple_wood", "stripped_japanese_maple_wood"
    ),

    WILLOW(
        TERRESTRIA.id("willow"),
        "willow_planks", "willow_slab",
        "willow_log", "stripped_willow_log",
        "willow_wood", "stripped_willow_wood"
    ),

    RUBBER(
        TERRESTRIA.id("rubber"),
        "rubber_planks", "rubber_slab",
        "rubber_log", "stripped_rubber_log",
        "rubber_wood", "stripped_rubber_wood"
    );

     */

    private val id: ResourceLocation = id

    @Nullable
    private val plankId: ResourceLocation? = if ((plank == null)) null else ResourceLocation(id.getNamespace(), plank)

    @Nullable
    private val plankSlabId: ResourceLocation? =
        if ((plankSlab == null)) null else ResourceLocation(id.getNamespace(), plankSlab)

    @Nullable
    private val logId: ResourceLocation? = if ((log == null)) null else ResourceLocation(id.getNamespace(), log)

    @Nullable
    private val strippedLogId: ResourceLocation? =
        if ((strippedLog == null)) null else ResourceLocation(id.getNamespace(), strippedLog)

    @Nullable
    private val woodId: ResourceLocation? = if ((wood == null)) null else ResourceLocation(id.getNamespace(), wood)

    @Nullable
    private val strippedWoodId: ResourceLocation? =
        if ((strippedWood == null)) null else ResourceLocation(id.getNamespace(), strippedWood)

    fun getId(): ResourceLocation {
        return id
    }

    @Nullable
    fun getPlankId(): ResourceLocation {
        return plankId!!
    }

    @Nullable
    fun getPlankSlabId(): ResourceLocation {
        return plankSlabId!!
    }

    @Nullable
    fun getLogId(): ResourceLocation {
        return logId!!
    }

    @Nullable
    fun getStrippedLogId(): ResourceLocation {
        return strippedLogId!!
    }

    @Nullable
    fun getWoodId(): ResourceLocation {
        return woodId!!
    }

    @Nullable
    fun getStrippedWoodId(): ResourceLocation {
        return strippedWoodId!!
    }

    val isPlankExist: Boolean
        get() = plankId != null

    val isPlankSlabExist: Boolean
        get() = plankSlabId != null

    val isLogExist: Boolean
        get() = logId != null

    val isStrippedLogExist: Boolean
        get() = strippedLogId != null

    val isWoodExist: Boolean
        get() = woodId != null

    val isStrippedWoodExist: Boolean
        get() = strippedWoodId != null

    fun check() {
        if (!BuiltInRegistries.ITEM.containsKey(this.getLogId()) && this.getLogId() != null) throwError(
            this.getLogId())
        if (!BuiltInRegistries.ITEM.containsKey(this.getStrippedLogId()) && this.getStrippedLogId() != null) throwError(
            this.getStrippedLogId())
        if (!BuiltInRegistries.ITEM.containsKey(this.getWoodId()) && this.getWoodId() != null) throwError(
            this.getWoodId())
        if (!BuiltInRegistries.ITEM.containsKey(this.getStrippedWoodId()) && this.getStrippedWoodId() != null) throwError(
            this.getStrippedWoodId())
        if (!BuiltInRegistries.ITEM.containsKey(this.getPlankId()) && this.getPlankId() != null) throwError(
            this.getPlankId())
        if (!BuiltInRegistries.ITEM.containsKey(this.getPlankSlabId()) && this.getPlankSlabId() != null) throwError(
            this.getPlankSlabId())
    }

    companion object {
        private fun throwError(id: ResourceLocation?) {
            throw RuntimeException("WoodCuttingEntry requires $id item which is not valid!")
        }

        fun get(id: ResourceLocation?): WoodCuttingEntry? {
            for (entry in entries) if (entry.getId().equals(id)) return entry
            return null
        }

        fun checkAll() {
            for (entry in entries) entry.check()
        }
    }
}