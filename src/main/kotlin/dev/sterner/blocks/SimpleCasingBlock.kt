package dev.sterner.blocks

import com.simibubi.create.content.decoration.encasing.CasingBlock
import dev.sterner.Fabricality
import dev.sterner.data.FBlockStatesGenerator
import dev.sterner.interfaces.ResourcedBlock
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.resources.ResourceLocation
import javax.annotation.Nullable


class SimpleCasingBlock(properties: Properties?, var name: String) : CasingBlock(properties), ResourcedBlock {

    override fun doBlockStates(): Boolean {
        return true
    }

    @Nullable
    override fun getBlockStates(): BlockStateGenerator {
        return FBlockStatesGenerator.simple(getBaseBlock(), getBlockModelId())
    }

    override fun getBlockModelId(): ResourceLocation {
        return Fabricality.id("block", "casing", this.name + "_casing")
    }

    override fun doLootTable(): Boolean {
        return true
    }
}