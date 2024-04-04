package dev.sterner.item

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem
import dev.sterner.Fabricality
import dev.sterner.interfaces.IncompleteVariant
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity


class MechanismItem(private val mechanismName: String, settings: Properties) : Item(settings), IncompleteVariant {

    override val incompleteTextureId: ResourceLocation
        get() = Fabricality.id("item", "mechanism", "incomplete", "incomplete_" + mechanismName + "_mechanism")

    val textureId: ResourceLocation
        get() = Fabricality.id("item", "mechanism", mechanismName + "_mechanism")

    enum class Type(val typeName: String, rarity: Rarity = Rarity.COMMON) {
        KINETIC("kinetic"),
        SEALED("sealed"),
        INFERNAL("infernal"),
        STURDY("sturdy"),
        INDUCTIVE("inductive"),
        ABSTRUSE("abstruse", Rarity.UNCOMMON),
        CALCULATION("calculation", Rarity.UNCOMMON);

        val item: MechanismItem
        val incompleteItem: SequencedAssemblyItem

        init {
            val newItem = MechanismItem(typeName, FabricItemSettings().rarity(rarity))
            this.item = newItem
            this.incompleteItem = newItem.newIncomplete(rarity)!!
        }

        val itemId: ResourceLocation
            get() = Fabricality.id(typeName + "_mechanism")

        val incompleteItemId: ResourceLocation
            get() = Fabricality.id("incomplete_" + typeName + "_mechanism")
    }
}