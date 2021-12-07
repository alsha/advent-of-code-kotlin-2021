import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    fun calc(input: List<String>, distanceToFuelFunc: (Int) -> Int): Int {
        val crabPositions = input.first().split(",").map { it.toInt() }

        val min = crabPositions.minOf { it }
        val max = crabPositions.maxOf { it }

        val groups = crabPositions.groupingBy { it }.eachCount()

        val result = (min..max).map { currentPos ->
            groups.map { group -> distanceToFuelFunc(abs(group.key - currentPos)) * group.value }.sum()
        }.minOf { it }

        return result
    }

    fun part1(input: List<String>): Int {
        return calc(input) { it }
    }

    fun part2(input: List<String>): Int {
        return calc(input) { (1..it).sum() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
