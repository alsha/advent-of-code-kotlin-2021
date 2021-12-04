fun main() {

    fun part1(input: List<String>): Int {
        val cols = input[0].length

        var gamma = (1..cols).map { index -> input.map { it[index - 1] } }
            .map { column -> column.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key }
            .joinToString(separator = "").toInt(2)

        var epsilon = (1..cols).map { index -> input.map { it[index - 1] } }
            .map { column -> column.groupingBy { it }.eachCount().minByOrNull { it.value }?.key }
            .joinToString(separator = "").toInt(2)

        return gamma * epsilon
    }

    fun eachCount(
        input: List<String>,
        position: Int
    ) = input.map { it[position] }.groupingBy { it }.eachCount()

    fun mostCommonAt(input: List<String>, position: Int): Char {
        val eachCount = eachCount(input, position)
        if (eachCount.size == 1) return eachCount.keys.first()
        return if (eachCount.getOrDefault('1', 0) >= eachCount.getOrDefault('0', 0)) '1' else '0'
    }

    fun leastCommonAt(input: List<String>, position: Int): Char {
        val eachCount = eachCount(input, position)
        if (eachCount.size == 1) return eachCount.keys.first()
        return if (eachCount.getOrDefault('1', 0) < eachCount.getOrDefault('0', 0)) '1' else '0'
    }

    fun filter(
        input: List<String>,
        commonFunc: (List<String>, Int) -> Char
    ): Int {
        val cols = input[0].length

        var filteredInput = input;
        //println(filteredInput)
        (0 until cols).forEach { index ->
            val c = commonFunc(filteredInput, index)
            filteredInput = filteredInput.filter { it[index] == c }
            //println("$c: $filteredInput")
        }
        //println(filteredInput)
        return filteredInput.joinToString(separator = "").toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxygen = filter(input) { input, position -> mostCommonAt(input, position) }
        val co2 = filter(input) { input, position -> leastCommonAt(input, position) }
        return oxygen * co2
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    //part2(testInput)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
