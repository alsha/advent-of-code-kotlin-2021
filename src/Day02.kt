fun main() {
    fun sumByCommand(input: List<String>, command: String) =
        input.map { it.split(" ") }.filter { it[0] == command }.sumOf { it[1].toInt() }

    fun part1(input: List<String>): Int {
        return sumByCommand(input, "forward") * (sumByCommand(input, "down") - sumByCommand(input, "up"))
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var depth = 0
        var horizontalPosition = 0
        input.map { it.split(" ") }.forEach {
            val x = it[1].toInt()
            when (it[0]) {
                "up" -> aim -= x
                "down" -> aim += x
                "forward" -> {
                    horizontalPosition += x
                    depth += x * aim
                }
            }
        }
        return depth * horizontalPosition
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
