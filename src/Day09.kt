import kotlin.system.measureTimeMillis

fun main() {

    data class Point(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toCharArray().map { it.toString().toInt() } }

        //println(matrix)

        var result = 0

        for (x in matrix.indices) {
            val row = matrix[x]
            for (y in row.indices) {

                val neighbours = mutableSetOf<Int>()

                //above
                if (x > 0) {
                    neighbours.add(matrix[x - 1][y])
                }
                //below
                if (x < matrix.size - 1) {
                    neighbours.add(matrix[x + 1][y])
                }
                //left
                if (y > 0) {
                    neighbours.add(matrix[x][y - 1])
                }
                //right
                if (y < matrix[x].size - 1) {
                    neighbours.add(row[y + 1])
                }

                if (neighbours.filter { it <= row[y] }.isEmpty()) {
                    //lowPoints.add(Pair(i,y))
                    result += row[y] + 1
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toCharArray().map { it.toString().toInt() } }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    //check(part2(testInput) == 1134)

    val input = readInput("Day09")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        //println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
