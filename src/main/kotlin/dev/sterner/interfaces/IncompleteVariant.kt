package dev.sterner.interfaces

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity

interface IncompleteVariant {
    fun newIncomplete(rarity: Rarity?): SequencedAssemblyItem? {
        return SequencedAssemblyItem(this.incompleteSettings(rarity))
    }

    fun getIncompleteId(id: ResourceLocation): ResourceLocation? {
        return ResourceLocation(id.getNamespace(), "incomplete_" + id.getPath())
    }

    fun incompleteSettings(rarity: Rarity?): Item.Properties? {
        return FabricItemSettings().rarity(rarity)
    }

    val incompleteTextureId: ResourceLocation?
}