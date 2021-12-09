import kotlin.system.measureTimeMillis

fun main() {

    data class Point(val i: Int, val j: Int)

    fun toGround(input: List<String>) =
        input.map { oneString -> oneString.toCharArray().map { digitChar -> digitChar.toString().toInt() } }

    fun neighbours(
        p: Point,
        ground: List<List<Int>>
    ): MutableSet<Point> {
        val neighbours = mutableSetOf<Point>()
        //above
        if (p.i > 0) {
            neighbours.add(Point(p.i - 1, p.j))
        }
        //below
        if (p.i < ground.size - 1) {
            neighbours.add(Point(p.i + 1, p.j))
        }
        //left
        if (p.j > 0) {
            neighbours.add(Point(p.i, p.j - 1))
        }
        //right
        if (p.j < ground[p.i].size - 1) {
            neighbours.add(Point(p.i, p.j + 1))
        }
        return neighbours
    }

    fun lowPoints(ground: List<List<Int>>): MutableSet<Point> {
        val lowPoints = mutableSetOf<Point>()

        for (i in ground.indices) {
            val row = ground[i]
            for (j in row.indices) {
                val neighbours = neighbours(Point(i, j), ground)
                if (neighbours.map { point -> ground[point.i][point.j] }.none { it <= row[j] }) {
                    lowPoints.add(Point(i, j))
                }
            }
        }
        return lowPoints
    }

    fun fillBasin(startPoint: Point, ground: List<List<Int>>, basin: MutableSet<Point>): MutableSet<Point> {
        basin.add(startPoint)
        val neighbours = neighbours(startPoint, ground)
        val newPoints = neighbours.filter { point -> !basin.contains(point) && ground[point.i][point.j] != 9 }
        newPoints.forEach { point -> fillBasin(point, ground, basin) }
        return basin
    }

    fun part1(input: List<String>): Int {
        val ground = toGround(input)
        val lowPoints = lowPoints(ground)
        return lowPoints.sumOf { point -> ground[point.i][point.j] + 1 }
    }

    fun part2(input: List<String>): Int {
        val ground = toGround(input)
        val lowPoints = lowPoints(ground)
        val sorted = lowPoints.map { p -> fillBasin(p, ground, mutableSetOf()) }.map { it.size }.sorted()
        return sorted.takeLast(3).reduce { acc, x -> acc * x }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
