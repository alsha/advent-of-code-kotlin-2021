fun main() {

    fun getInputNumbers(input: List<String>) = input[0].split(",").map { it.toInt() }

    fun getInputBoards(input: List<String>) =
        input.drop(2).chunked(6).map { listOfSixLines -> listOfSixLines.filter { it.trim().isNotEmpty() } }
            .map { listOfFiveLines ->
                listOfFiveLines.map { oneLine ->
                    oneLine.trim().split("\\s+".toRegex()).map { Pair(it.toInt(), false) }
                }
            }

    fun getNewBoardWithMarkeNumber(
        board: List<List<Pair<Int, Boolean>>>,
        numberToMark: Int
    ): List<List<Pair<Int, Boolean>>> {
        return board.map { row -> row.map { pair -> if (pair.first == numberToMark) Pair(pair.first, true) else pair } }
    }

    fun hasFilledRow(board: List<List<Pair<Int, Boolean>>>): Boolean =
        board.map { row -> row.count { pair -> pair.second } }.any { it == 5 }

    fun hasFilledCol(board: List<List<Pair<Int, Boolean>>>): Boolean =
        hasFilledRow(transpose(board))

    fun calcBoardScore(
        board: List<List<Pair<Int, Boolean>>>,
        winNumber: Int
    ) = board.flatMap { row -> row.filter { !it.second } }.sumOf { it.first } * winNumber

    fun isWon(board: List<List<Pair<Int, Boolean>>>) =
        hasFilledRow(board) || hasFilledCol(board)

    fun part1(input: List<String>): Int {
        val numbers = getInputNumbers(input)
        var boards = getInputBoards(input)

        numbers.forEach { drawnNumber ->
            boards
                .map { board -> getNewBoardWithMarkeNumber(board, drawnNumber) }
                .also { boards = it }
            val wonBoard = boards
                .firstOrNull { board -> isWon(board) }

            if (wonBoard != null) {
                return calcBoardScore(wonBoard, drawnNumber)
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = getInputNumbers(input)
        var boards = getInputBoards(input)
        var lastWonBoardIndex = -1;

        for (drawnNumber in numbers) {
            boards
                .map { board -> getNewBoardWithMarkeNumber(board, drawnNumber) }
                .also { boards = it }

            val wonBoardsCount = boards
                .filter { board -> isWon(board) }
                .count()

            if (wonBoardsCount == boards.size - 1) {
                val lastWinBoard = boards
                    .firstOrNull { board -> !isWon(board) }
                lastWonBoardIndex = boards.indexOf(lastWinBoard)
            } else if (wonBoardsCount == boards.size) {
                if (lastWonBoardIndex != -1) {
                    return calcBoardScore(boards[lastWonBoardIndex], drawnNumber)
                }
            }
        }

        return -1
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    part1(testInput)
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
