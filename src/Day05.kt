fun main() {

    fun part1(input: List<String>): Int {

        return -1
    }

    fun part2(input: List<String>): Int {

        return -1
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    part1(testInput)
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
