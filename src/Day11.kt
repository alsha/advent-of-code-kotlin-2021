import kotlin.system.measureTimeMillis

fun main() {

    data class Point(val i: Int, val j: Int)

    data class Result(val totalFlashCount: Int, val step: Int)

    fun toGround(input: List<String>) =
        input.map { oneString ->
            oneString.toCharArray().map { digitChar -> digitChar.toString().toInt() }.toMutableList()
        }.toMutableList()

    fun neighbours(
        p: Point,
        ground: List<List<Int>>
    ): MutableSet<Point> {
        val neighbours = mutableSetOf<Point>()
        //above
        if (p.i > 0) {
            neighbours.add(Point(p.i - 1, p.j))
        }
        //above right
        if (p.i > 0 && p.j < ground[p.i].size - 1) {
            neighbours.add(Point(p.i - 1, p.j + 1))
        }
        //right
        if (p.j < ground[p.i].size - 1) {
            neighbours.add(Point(p.i, p.j + 1))
        }
        //below right
        if (p.j < ground[p.i].size - 1 && p.i < ground.size - 1) {
            neighbours.add(Point(p.i + 1, p.j + 1))
        }
        //below
        if (p.i < ground.size - 1) {
            neighbours.add(Point(p.i + 1, p.j))
        }
        //below left
        if (p.i < ground.size - 1 && p.j > 0) {
            neighbours.add(Point(p.i + 1, p.j - 1))
        }
        //left
        if (p.j > 0) {
            neighbours.add(Point(p.i, p.j - 1))
        }
        //above left
        if (p.j > 0 && p.i > 0) {
            neighbours.add(Point(p.i - 1, p.j - 1))
        }
        return neighbours
    }

    fun flashRecursive(
        startingPoint: Point,
        ground: MutableList<MutableList<Int>>,
        flashedPoints: MutableSet<Point>
    ) {
        val energy = ground[startingPoint.i][startingPoint.j]
        if (energy > 9 && !flashedPoints.contains(startingPoint)) {
            flashedPoints.add(startingPoint)
            val neighbours = neighbours(startingPoint, ground)
            ground[startingPoint.i][startingPoint.j] = 0
            neighbours.forEach {
                if (!flashedPoints.contains(it)) {
                    ground[it.i][it.j]++
                    flashRecursive(it, ground, flashedPoints)
                }
            }
        }
    }

    fun increaseEnergyByOne(ground: MutableList<MutableList<Int>>) {
        ground.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                ground[i][j]++
            }
        }
    }

    fun printGround(index: Int, ground: MutableList<MutableList<Int>>) {
        println("After step ${index + 1}")
        ground.forEach { row ->
            println(row.joinToString(""))
        }
        println()
    }

    fun calc(
        input: List<String>,
        breakCondition: (ground: MutableList<MutableList<Int>>, step: Int, flashCountAtStep: Int) -> Boolean
    ): Result {
        val ground = toGround(input)

        var totalFlashCount = 0

        var currentStep = 1

        while (true) {
            increaseEnergyByOne(ground)

            val flashedPoints = mutableSetOf<Point>()

            ground.forEachIndexed { i, row ->
                row.forEachIndexed { j, _ ->
                    flashRecursive(Point(i, j), ground, flashedPoints)
                }
            }

            //printGround(index, ground)

            totalFlashCount += flashedPoints.size

            if (breakCondition(ground, currentStep, flashedPoints.size)) {
                break
            }

            currentStep++
        }

        return Result(totalFlashCount, currentStep)
    }

    fun part1(input: List<String>): Int {
        return calc(input) { _, step, _ -> step == 100 }.totalFlashCount
    }

    fun part2(input: List<String>): Int {
        return calc(input) { ground, _, flashCountAtStep -> flashCountAtStep == ground.size * ground[0].size }.step
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
