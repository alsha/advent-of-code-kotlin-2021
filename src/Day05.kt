import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {

    data class Point (val x: Int, val y: Int) {}

    data class Line (val start: Point, val end: Point) {}

    fun isLineVertical(line: List<List<Int>>) =
        line[0][0] == line[1][0]

    fun isLineHorizontal(line: List<List<Int>>) =
        line[0][1] == line[1][1]

    fun part1(input: List<String>): Int {
        val allLines = input
            .map { line ->
                line.trim().split("->")
                    .map { xy ->
                        xy.trim().split(",").map { it.toInt() }
                    }
            }

        val pointsOfAllHorizontalLines = allLines
            .filter { line -> isLineHorizontal(line) }
            .map { line ->
                (min(line[0][0], line[1][0])..max(line[0][0], line[1][0])).map { listOf(it, line[0][1]) }
            }.flatten()

        val pointsOfAllVerticalLines = allLines
            .filter { line -> isLineVertical(line) }
            .map { line ->
                (min(line[0][1], line[1][1])..max(line[0][1], line[1][1])).map { listOf(line[0][0], it) }
            }.flatten()


        val allPoints =  pointsOfAllHorizontalLines.plus(pointsOfAllVerticalLines)

        val result = allPoints
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }.size

        return result
    }

    fun part2(input: List<String>): Int {

        return -1
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    //part1(testInput)
    check(part1(testInput) == 5)
    //check(part2(testInput) == 1924)

    val input = readInput("Day05")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
    }
    println("(The operation took $timeInMillis ms)")
    //println(part2(input))
}
