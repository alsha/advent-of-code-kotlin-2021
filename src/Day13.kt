fun main() {

    data class Dot(val x: Int, val y: Int)

    data class FoldInstruction(val horizontal: Boolean, val position: Int)

    fun display(dots: MutableSet<Dot>) {
        (0..dots.maxOf { it.y }).forEach { y ->
            (0..dots.maxOf { it.x }).forEach { x ->
                if (dots.contains(Dot(x, y))) print("#") else print(".")
            }
            println()
        }
    }

    fun parseDots(input: List<String>) =
        input.filter { it.isNotEmpty() && it.first().isDigit() }
            .map { xy ->
                xy.split(",")
                    .map { it.toInt() }
            }.map { Dot(it[0], it[1]) }.toMutableSet()

    fun parseFoldInstructions(input: List<String>): List<FoldInstruction> {
        val prefix = "fold along "
        return input.filter { it.isNotEmpty() && it.startsWith(prefix) }.map { it.substring(prefix.length) }
            .map { it.split("=") }
            .map { FoldInstruction(it[0] == "y", it[1].toInt()) }

    }

    fun fold(input: List<String>, onlyFirstFoldInstruction: Boolean): Int {
        val dots = parseDots(input)

        var foldInstructions = parseFoldInstructions(input)

        if (onlyFirstFoldInstruction) {
            foldInstructions = foldInstructions.take(1)
        }

        foldInstructions.forEach { foldInstruction ->
            val foldedDots = mutableSetOf<Dot>()
            val removedDots = mutableSetOf<Dot>()
            dots.forEach { dot ->
                if (foldInstruction.horizontal) {
                    if (dot.y > foldInstruction.position) {
                        foldedDots.add(Dot(dot.x, 2 * foldInstruction.position - dot.y))
                        removedDots.add(dot)
                    }
                } else {
                    if (dot.x > foldInstruction.position) {
                        foldedDots.add(Dot(2 * foldInstruction.position - dot.x, dot.y))
                        removedDots.add(dot)
                    }
                }
            }
            dots.addAll(foldedDots)
            dots.removeAll(removedDots)
        }


        if (!onlyFirstFoldInstruction) {
            display(dots)
        }

        return dots.size
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
