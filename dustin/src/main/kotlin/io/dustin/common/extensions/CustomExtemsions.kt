package io.dustin.common.extensions

 /** 파이썬 zip 함수와 유사하다 */
fun <T, R> Iterable<T>.countZipWith(other: R): Pair<Iterable<T>, R> {
    return this to other
}

/**
 * fun main() {
 *     val numbersPair: Pair<List<Int>, Int> = listOf(1, 2, 3) to 5
 *
 *     val mappedResult: List<Int> = numbersPair.map { pair ->
 *         // 여기에서 pair.first는 List<Int>, pair.second는 Int입니다.
 *         pair.first.map { it * pair.second }
 *     }
 *
 *     println(mappedResult)
 * }
 */
fun <T, R, S> Pair<Iterable<T>, R>.map(transformer: (Pair<Iterable<T>, R>) -> S): S {
    return transformer(this)
}