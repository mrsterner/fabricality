package dev.sterner.interfaces

import dev.sterner.data.FBlockResourceGenerator

interface ResourcedBlock : FBlockResourceGenerator {

    fun doLootTable(): Boolean {
        return false
    }

    fun doModel(): Boolean {
        return false
    }

    fun doBlockStates(): Boolean {
        return false
    }

    fun doItemModel(): Boolean {
        return true
    }
}