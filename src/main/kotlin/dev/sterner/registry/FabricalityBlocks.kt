package dev.sterner.registry

import dev.sterner.Fabricality
import dev.sterner.blocks.*
import dev.sterner.interfaces.BlockItemSettable
import dev.sterner.interfaces.ResourcedBlock
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.*


object FabricalityBlocks {

    fun register() {

        Arrays.stream(MachineBlockEntry.entries.toTypedArray())
            .forEach { entry -> registerBlock(entry.getId().path, entry.getBlock()) }
        Arrays.stream(CasingBlockEntry.entries.toTypedArray())
            .forEach { entry -> registerBlock(entry.getId().path, entry.getBlock()) }
    }

    private fun registerBlock(name: String, block: Block): Block {
        val registered: Block = Registry.register(BuiltInRegistries.BLOCK, Fabricality.id(name), block)

        val blockItem: Item = Registry.register(
            BuiltInRegistries.ITEM,
            Fabricality.id(name),
            BlockItem(block, (if ((block is BlockItemSettable)) block.getSettings() else FabricItemSettings())!!)
        )

        ItemGroupEvents.modifyEntriesEvent(Fabricality.GENERAL_KEY).register(
            ModifyEntries {
                content: FabricItemGroupEntries -> content.accept(blockItem)
            })

        if (block is ResourcedBlock) {
            if (block.doModel()) block.writeBlockModel(Fabricality.RRPs.CLIENT_RESOURCES)
            if (block.doLootTable()) block.writeLootTable(Fabricality.RRPs.SERVER_RESOURCES)
            if (block.doBlockStates()) block.writeBlockStates(Fabricality.RRPs.CLIENT_RESOURCES)
            if (block.doItemModel()) block.writeItemModel(Fabricality.RRPs.CLIENT_RESOURCES)
        }

        return registered
    }
}