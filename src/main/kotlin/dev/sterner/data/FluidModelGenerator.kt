package dev.sterner.data

import dev.sterner.Fabricality
import pers.solid.brrp.v1.model.ModelJsonBuilder


object FluidModelGenerator {
    fun simple(id: String?, rawId: String?): ModelJsonBuilder {
        return ModelJsonBuilder().addTexture("particle", Fabricality.id("fluid", rawId, id).toString())
    }
}