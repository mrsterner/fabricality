package dev.sterner.blocks

class CutoffMachineBlock(settings: Properties) : AbstractMachineBlock(settings) {
    override fun isWaterLoggable(): Boolean {
        return true
    }

    override fun isFull(): Boolean {
        return true
    }
}