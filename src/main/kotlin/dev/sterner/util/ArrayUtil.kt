package dev.sterner.util

import java.util.stream.Stream


object ArrayUtil {
    @SafeVarargs
    inline fun <reified T> of(vararg objects: T): Array<T> {
        return arrayOf(*objects)
    }

    fun <T> asArrayList(stream: Stream<T>): ArrayList<T> {
        return stream.collect({ ArrayList<T>() }, { list, item -> list.add(item) }, { list1, list2 -> list1.addAll(list2) })
    }
}