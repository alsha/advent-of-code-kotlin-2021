import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    data class Point(val x: Int, val y: Int) {}

    data class Line(val start: Point, val end: Point) {}

    fun isLineVertical(line: Line) =
        line.start.x == line.end.x

    fun isLineHorizontal(line: Line) =
        line.start.y == line.end.y

    fun isLineDiagonal(line: Line) =
        abs(line.start.x - line.end.x) == abs(line.start.y - line.end.y)

    fun pointsOfDiagonalLine(line: Line): List<Point> =
        (line.start.x through line.end.x).zip(line.start.y through line.end.y).map { Point(it.first, it.second) }

    fun part1(input: List<String>, diagonal: Boolean = false): Int {
        val allLines = input
            .map { line ->
                line.trim().split("->")
                    .map { xy ->
                        xy.trim().split(",").map { it.toInt() }
                    }.map { Point(it[0], it[1]) }
            }.map { Line(it[0], it[1]) }

        val pointsOfAllHorizontalLines = allLines
            .filter { line -> isLineHorizontal(line) }
            .map { line ->
                (line.start.x through line.end.x).map { x -> Point(x, line.start.y) }
            }.flatten()

        val pointsOfAllVerticalLines = allLines
            .filter { line -> isLineVertical(line) }
            .map { line ->
                (line.start.y through line.end.y).map { y -> Point(line.start.x, y) }
            }.flatten()


        var allPoints = pointsOfAllHorizontalLines.plus(pointsOfAllVerticalLines)

        if (diagonal) {
            val pointsOfAllDiagonalLines = allLines
                .filter { line -> isLineDiagonal(line) }
                .map { line -> pointsOfDiagonalLine(line) }
                .flatten()

            allPoints = allPoints.plus(pointsOfAllDiagonalLines)
        }

        val result = allPoints
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }.size

        return result
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part1(testInput, true) == 12)

    val input = readInput("Day05")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part1(input, true))
    }
    println("(The operation took $timeInMillis ms)")
}

private infix fun Int.through(i: Int): Iterable<Int> {
    return if (this < i) this..i else this downTo i
}
