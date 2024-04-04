package dev.sterner.data

import dev.sterner.Fabricality
import pers.solid.brrp.v1.model.ModelJsonBuilder

object ItemModelGenerator {
    fun generated(vararg id: String?): ModelJsonBuilder {
        return ModelJsonBuilder().parent("item/generated")
            .addTexture("layer0", Fabricality.id(java.lang.String.join("/", *id)).toString())
    }

    fun parented(vararg parent: String?): ModelJsonBuilder {
        return ModelJsonBuilder().parent(java.lang.String.join("/", *parent))
    }
}