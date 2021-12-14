fun main() {

    fun part1(input: List<String>): Int {
        val polymerTemplate = input.first()

        val insertionRules = input
            .drop(2)
            .map { it.split("->") }
            .associate { it[0].trim() to it[1].trim() }

        var polymer = polymerTemplate

        repeat(10) {
            polymer
                .windowed(2, 1)
                .map { pair -> pair[0] + insertionRules[pair].toString() + pair[1] }
                .reduce { result, current -> result + current.substring(1) }
                .also { polymer = it }
        }

        val eachCount = polymer.groupingBy { it }.eachCount()

        return eachCount.maxOf { it.value } - eachCount.minOf { it.value }
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    //part2(testInput)
    println()

    val input = readInput("Day14")
    println(part1(input))
    //part2(input)
}
