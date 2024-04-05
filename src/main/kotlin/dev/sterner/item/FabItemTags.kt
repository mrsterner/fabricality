package dev.sterner.item

import dev.sterner.Fabricality
import dev.sterner.util.ModCompat
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item


object FabItemTags {
    val SAWS: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("c", "saws"))

    // Trading Cards
    val TRADE_CARDS: TagKey<Item> = TagKey.create(Registries.ITEM, Fabricality.id("trade_cards"))
    val PROFESSION_CARDS: TagKey<Item> = TagKey.create(Registries.ITEM, Fabricality.id("profession_cards"))

    // Jars
    val JARS: TagKey<Item> = TagKey.create(Registries.ITEM, Fabricality.id("jars"))
    val REAGENT_JARS: TagKey<Item> = TagKey.create(Registries.ITEM, Fabricality.id("jars/reagent"))
    val CATALYST_JARS: TagKey<Item> = TagKey.create(Registries.ITEM, Fabricality.id("jars/catalyst"))

    // Stripped
    val STRIPPED_LOGS: TagKey<Item> = TagKey.create(Registries.ITEM, ModCompat.Entry.C.id("stripped_logs"))
    val STRIPPED_WOODS: TagKey<Item> = TagKey.create(Registries.ITEM, ModCompat.Entry.C.id("stripped_wood"))
}