package dev.sterner.util


object ListUtil {
    fun <T> loop(`object`: T, times: Int): List<T> {
        val list = ArrayList<T>()
        for (i in 0 until times) list.add(`object`)
        return list
    }
}