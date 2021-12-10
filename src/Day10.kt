import java.util.*
import kotlin.system.measureTimeMillis

fun main() {

    data class ParseState(val firstInvalidBracket: Char?, val completionSequence: List<Char>)

    val openToCloseBracket = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val bracketToScore1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val bracketToScore2 = mapOf(')' to 1L, ']' to 2L, '}' to 3L, '>' to 4L)

    fun parse(it: List<Char>): ParseState {
        val stack = ArrayDeque<Char>()
        var firstInvalidBracket: Char? = null
        for (inputBracket in it) {
            if (openToCloseBracket.containsKey(inputBracket)) {
                stack.push(inputBracket)
            } else if (inputBracket != openToCloseBracket[stack.pop()]) {
                firstInvalidBracket = inputBracket
                break
            }
        }
        return ParseState(firstInvalidBracket, stack.toList())
    }

    fun convertInput(input: List<String>) = input.asSequence().map { it.toCharArray().asList() }

    fun part1(input: List<String>): Int {
        return convertInput(input)
            .map { parse(it) }
            .map { bracketToScore1[it.firstInvalidBracket] }
            .sumOf { it ?: 0 }
    }

    fun part2(input: List<String>): Long {
        val sortedScores = convertInput(input)
            .map { parse(it) }
            .filter { it.firstInvalidBracket == null }
            .map { parseState ->
                parseState.completionSequence.map { openToCloseBracket[it] }.map { bracketToScore2[it] }
            }
            .map { it.fold(0L) { acc, c -> acc * 5L + c!! } }
            .sorted().toList()

        return sortedScores[sortedScores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    val timeInMillis = measureTimeMillis {
        println(part1(input))
        println(part2(input))
    }
    println("(The operation took $timeInMillis ms)")
}
