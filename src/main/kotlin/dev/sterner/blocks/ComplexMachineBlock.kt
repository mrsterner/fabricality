package dev.sterner.blocks

class ComplexMachineBlock(settings: Properties) : AbstractMachineBlock(settings) {

    override fun isWaterLoggable(): Boolean {
        return true
    }

    override fun isFull(): Boolean {
        return false
    }
}