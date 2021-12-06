import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {

    data class FishGroup(var timer: Int, val count: BigInteger) {
        fun nextDay(): FishGroup? {
            if (this.timer == 0) {
                this.timer = 6
                return FishGroup(8, count)
            }
            this.timer--
            return null
        }
    }

    fun compactGroups(fishGroups: MutableList<FishGroup>): MutableList<FishGroup> {
        return fishGroups
            .groupBy { it.timer }.map { FishGroup(it.key, it.value.sumOf { group -> group.count }) }
            .toMutableList()
    }

    fun getNewGroups(fishGroups: MutableList<FishGroup>) = fishGroups.mapNotNull { it.nextDay() }

    fun calc(input: List<String>, days: Int): BigInteger {
        var fishGroups = input.first().split(",").map { it.toInt() }.groupingBy { it }.eachCount()
            .map { FishGroup(it.key, it.value.toBigInteger()) }.toMutableList()

        for (day in 1..days) {
            fishGroups.addAll(getNewGroups(fishGroups))
            fishGroups = compactGroups(fishGroups)
            println("Day: $day; fishGroups: $fishGroups")
        }

        return fishGroups.map { it.count }.sumOf { it }
    }

    fun part1(input: List<String>): BigInteger {
        return calc(input, 80)
    }

    fun part2(input: List<String>): BigInteger {
        return calc(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == BigInteger.valueOf(5934))
    check(part2(testInput) == BigInteger.valueOf(26984457539))

    val input = readInput("Day06")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
