import kotlin.system.measureTimeMillis

fun main() {

    class Fish (var timer: Int) {
        fun nextDay () : Boolean {
            if (this.timer == 0) {
                this.timer = 6;
                return true;
            }
            this.timer--
            return false
        }
    }

    fun calc(input: List<String>, days: Int): Int {
        val fishList = input.first().split(",").map { Fish(it.toInt()) }.toMutableList()

        for (day in (1..days)) {

            val newFishes = fishList.map { it.nextDay() }.filter { it }.count()

            println("Day: $day; newFishes: $newFishes")

            repeat(newFishes) { fishList.add(Fish(8)) }
        }

        return fishList.size
    }

    fun part1(input: List<String>): Int {
        return calc(input, 80)
    }

    fun part2(input: List<String>): Int {
        return calc(input, 256)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    //check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        //println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
