package dev.sterner.client


object FluidColorRegistry {
    private val colors: MutableMap<String, Int> = HashMap()

    fun register(fluid: String, color: Int) {
        if (!colors.containsKey(fluid)) colors[fluid] = color
    }

    fun get(name: String): Int {
        if (!colors.containsKey(name)) return -1
        return colors[name]!!
    }
}