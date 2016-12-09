package eightbitlab

fun <T, V> MutableMap<T, V?>.swapKeys(key1: T, key2: T) {
    val temp = this[key1]
    this[key1] = this[key2]
    this[key2] = temp
}