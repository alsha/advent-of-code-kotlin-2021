fun main() {

    data class Point(val x: Int, val y: Int)

    data class Fold(val horizontal: Boolean, val position: Int)

    fun fold(input: List<String>, takeOnlyFirstFold: Boolean): Int {
        val points =
            input.filter { it.isNotEmpty() && it.first().isDigit() }
                .map { xy ->
                    xy.split(",")
                        .map { it.toInt() }
                }.map { Point(it[0], it[1]) }.toMutableSet()

        val prefix = "fold along "
        var folds =
            input.filter { it.isNotEmpty() && it.startsWith(prefix) }.map { it.substring(prefix.length) }
                .map { it.split("=") }
                .map { Fold(it[0] == "y", it[1].toInt()) }


        if (takeOnlyFirstFold) {
            folds = folds.take(1)
        }

        folds
            .forEach { fold ->
                val foldedPoints = mutableSetOf<Point>()
                val removedPoints = mutableSetOf<Point>()
                points.forEach { point ->
                    if (fold.horizontal) {
                        if (point.y > fold.position) {
                            foldedPoints.add(Point(point.x, 2 * fold.position - point.y))
                            removedPoints.add(point)
                        }
                    } else {
                        if (point.x > fold.position) {
                            foldedPoints.add(Point(2 * fold.position - point.x, point.y))
                            removedPoints.add(point)
                        }
                    }
                }
                points.addAll(foldedPoints)
                points.removeAll(removedPoints)
            }


        if (!takeOnlyFirstFold) {
            (0..points.maxOf { it.y }).forEach { y ->
                (0..points.maxOf { it.x }).forEach { x ->
                    if (points.contains(Point(x, y))) print("#") else print(".")
                }
                println()
            }
        }

        return points.size
    }

    fun part1(input: List<String>): Int {
        return fold(input, true)
    }

    fun part2(input: List<String>): Int {
        return fold(input, false)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)
    println()

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
