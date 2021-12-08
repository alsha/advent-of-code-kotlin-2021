import kotlin.system.measureTimeMillis

fun main() {

    fun part1(input: List<String>): Int {
        val digitLengths = listOf(2, 4, 3, 7) // 1,4,7,8
        return input.map { it.split("|")[1].trim().split(" ") }.flatten().map { it.trim() }
            .filter { digitLengths.contains(it.length) }.size
    }

    fun filterByRule(uniquePatterns: MutableList<List<Char>>, rule: (List<Char>) -> Boolean): List<Char> {
        return uniquePatterns.first { rule(it) }.also { uniquePatterns.remove(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split("|").map { it.trim().split(" ").map { it.toCharArray().sorted() } } }
            .map {
                var uniquePatterns = it[0].toMutableList()

                var patternForDigit = arrayOfNulls<List<Char>>(10)

                patternForDigit[1] = filterByRule(uniquePatterns) { it.size == 2 }

                patternForDigit[4] = filterByRule(uniquePatterns) { it.size == 4 }

                patternForDigit[7] = filterByRule(uniquePatterns) { it.size == 3 }

                patternForDigit[8] = filterByRule(uniquePatterns) { it.size == 7 }

                patternForDigit[3] = filterByRule(uniquePatterns) {
                    it.size == 5 && (patternForDigit[1]?.intersect(it)?.size ?: -1) == 2
                }

                patternForDigit[5] = filterByRule(uniquePatterns) {
                    it.size == 5 && (patternForDigit[4]?.intersect(it)?.size ?: -1) == 3
                }

                patternForDigit[2] = filterByRule(uniquePatterns) { it.size == 5 }

                patternForDigit[6] = filterByRule(uniquePatterns) {
                    it.size == 6 && (patternForDigit[1]?.intersect(it)?.size ?: -1) == 1
                }

                patternForDigit[9] = filterByRule(uniquePatterns) {
                    it.size == 6 && (patternForDigit[3]?.intersect(it)?.size ?: -1) == 5
                }

                patternForDigit[0] = uniquePatterns.first()

                it[1].map {
                    patternForDigit.indexOf(it)
                }.joinToString("").toInt()
            }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
