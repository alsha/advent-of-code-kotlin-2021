fun main() {

    fun upsertValue(
        pairGroups: MutableMap<String, Long>,
        key: String,
        value: Long
    ) {
        if (pairGroups[key] != null) pairGroups[key] = pairGroups[key]!! + value
        else pairGroups[key] = value
    }

    fun toPairGroups(polymerTemplate: String): MutableMap<String, Long> {
        val tmp = polymerTemplate.windowed(2, 1).groupingBy { it }.eachCount()
        val pairGroups = mutableMapOf<String, Long>()
        tmp.forEach { pairGroups[it.key] = it.value.toLong() }
        return pairGroups
    }

    fun calc(input: List<String>, steps: Int): Long {
        val polymerTemplate = input.first()

        val insertionRules = input
            .drop(2)
            .map { it.split("->") }
            .associate { it[0].trim() to it[1].trim() }

        val pairGroups = toPairGroups(polymerTemplate)

        repeat(steps) {

            val addedPairs = mutableMapOf<String, Long>()
            val removedPairs = mutableMapOf<String, Long>()

            pairGroups.forEach { entry ->

                val insertion = insertionRules[entry.key]

                if (insertion != null) {
                    upsertValue(addedPairs, entry.key[0] + insertion, entry.value)
                    upsertValue(addedPairs, insertion + entry.key[1], entry.value)
                    upsertValue(removedPairs, entry.key, entry.value)
                }
            }

            removedPairs.forEach {
                upsertValue(pairGroups, it.key, -it.value)
            }

            addedPairs.forEach { entry ->
                upsertValue(pairGroups, entry.key, entry.value)
            }
        }

        val elementGroups = mutableMapOf<String, Long>()

        pairGroups.forEach { entry ->
            val key = entry.key[1].toString()
            upsertValue(elementGroups, key, entry.value)
        }

        return elementGroups.maxOf { it.value } - elementGroups.minOf { it.value }
    }

    fun part1(input: List<String>): Long {
        return calc(input, 10)
    }

    fun part2(input: List<String>): Long {
        return calc(input, 40)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
