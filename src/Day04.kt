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
        winBoard: List<List<Pair<Int, Boolean>>>,
        drawnNumber: Int
    ) = winBoard.flatMap { row -> row.filter { !it.second } }.sumOf { it.first } * drawnNumber

    fun part1(input: List<String>): Int {
        val numbers = getInputNumbers(input)
        var boards = getInputBoards(input)

        numbers.forEach { drawnNumber ->
            boards
                .map { board -> getNewBoardWithMarkeNumber(board, drawnNumber) }
                .also { boards = it }
            val winBoard = boards
                .firstOrNull { board -> hasFilledRow(board) || hasFilledCol(board) }

            if (winBoard != null) {
                return calcBoardScore(winBoard, drawnNumber)
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = getInputNumbers(input)
        var boards = getInputBoards(input)
        var lastWinBoardIndex = -1;

        for (drawnNumber in numbers) {
            boards
                .map { board -> getNewBoardWithMarkeNumber(board, drawnNumber) }
                .also { boards = it }

            val winBoardsCount = boards
                .filter { board -> hasFilledRow(board) || hasFilledCol(board) }
                .count()

            if (winBoardsCount == boards.size - 1) {
                val lastWinBoard = boards
                    .firstOrNull { board -> !(hasFilledRow(board) || hasFilledCol(board)) }
                lastWinBoardIndex = boards.indexOf(lastWinBoard)
            } else if (winBoardsCount == boards.size) {
                if (lastWinBoardIndex != -1) {
                    return calcBoardScore(boards[lastWinBoardIndex], drawnNumber)
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
