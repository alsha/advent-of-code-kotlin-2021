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

    fun mostCommonAt(input: List<String>, position: Int): Char {
        val eachCount = input.map { it[position] }.groupingBy { it }.eachCount()

        return if (eachCount.getOrDefault('1', 0) >= eachCount.getOrDefault('0', 0)) '1' else '0'
    }

    fun leastCommonAt(input: List<String>, position: Int): Char {
        val eachCount = input.map { it[position] }.groupingBy { it }.eachCount()

        return if (eachCount.getOrDefault('1', 0) < eachCount.getOrDefault('0', 0)) '1' else '0'
    }

    fun part2(input: List<String>): Int {
        val cols = input[0].length

        var filteredInput = input;
        //println(filteredInput)
        run loop@{
            (0 until cols).forEach { index ->
                val c = mostCommonAt(filteredInput, index)
                filteredInput = filteredInput.filter { it[index] == c }
                //println("$c: $filteredInput")
                if (filteredInput.size == 1) {
                    return@loop
                }
            }
        }
        //println(filteredInput)
        val oxygen = filteredInput.joinToString(separator = "").toInt(2)

        filteredInput = input;
        run loop@{
            (0 until cols).forEach { index ->
                val c = leastCommonAt(filteredInput, index)
                //println("$index: $c")
                filteredInput = filteredInput.filter { it[index] == c }
                //println("$c: $filteredInput")
                if (filteredInput.size == 1) {
                    return@loop
                }
            }
        }
        //println(filteredInput)
        val co2 = filteredInput.joinToString(separator = "").toInt(2)

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
