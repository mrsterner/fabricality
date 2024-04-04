package dev.sterner.blocks

class SolidMachineBlock(settings: Properties) : AbstractMachineBlock(settings) {
    override fun isWaterLoggable(): Boolean {
        return false
    }

    override fun isFull(): Boolean {
        return true
    }
}