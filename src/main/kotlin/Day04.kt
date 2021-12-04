import Colors.green
import Colors.red

fun main() = Day04()()

class Day04(test: Boolean = false) : Day(4, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
    }.filterNotNull()

    fun getDraw(input: List<String>) = input.first().split(",").map(String::toInt)

    fun getBoards(input: List<String>) = input.drop(1)
        .map { line ->
            line.split(" ")
                .filter { it.isNotBlank() }
                .map { Case(it.trim().toInt()) }
        }
        .chunked(5)
        .map { Board(it) }

    fun getBoardsRec(input: List<String>) = input.drop(1)
        .mapIndexed { yIdx, line ->
            line.split(" ")
                .filter { it.isNotBlank() }
                .mapIndexed { xIdx, value -> CaseRec(xIdx, yIdx % 5, value.trim().toInt()) }
        }
        .chunked(5)
        .map { BoardRec(it.flatten()) }

    override fun invoke() {
        val input = input()
        val draw = getDraw(input)

        part1(input) {
            val boards = getBoards(input)
            resolvePart1(draw, boards).toString()
        }

        part1(input, "REC") {
            val boardsRec = getBoardsRec(input)
            resolvePart1Rec(draw, boardsRec).toString()
        }

        part2(input) {
            val boards = getBoards(input)
            resolvePart2(draw, boards).toString()
        }

        part2(input, "REC") {
            val boardsRec = getBoardsRec(input)
            resolvePart2Rec(draw, boardsRec).toString()
        }
    }

    fun resolvePart1(draw: List<Int>, boards: List<Board>): Int {
        draw.forEach { nb ->
            boards.forEach { board ->
                board.addNumber(nb)
                if (board.wins()) {
                    board.printIt()
                    return board.value() * nb
                }
            }
        }
        return 0
    }

    tailrec fun recPart1(draw: List<Int>, boards: List<BoardRec>): Int = when {
        draw.isEmpty() -> 0
        else -> {
            val currentNumber = draw.first()
            val newBoards = boards.map { it.play(currentNumber) }
            val firstWon = newBoards.firstOrNull { it.wins() }
            when {
                firstWon != null -> {
                    firstWon.printIt()
                    currentNumber * firstWon.value()
                }
                else -> recPart1(draw.drop(1), newBoards)
            }
        }
    }

    fun resolvePart1Rec(draw: List<Int>, boards: List<BoardRec>): Int =
        recPart1(draw, boards)


    fun resolvePart2(draw: List<Int>, boards: List<Board>): Int {
        draw.forEach { nb ->
            boards.forEach { board ->
                board.addNumber(nb)
                if (boards.all { it.wins() }) return board.value() * nb
            }
        }
        return 0
    }

    tailrec fun recPart2(lastWonValue: Int, draw: List<Int>, boards: List<BoardRec>): Int = when {
        draw.isEmpty() -> lastWonValue
        else -> {
            val nb = draw.first()
            val newBoards = boards.map { it.play(nb) }
            val lastWonBoard = newBoards.lastOrNull { it.wins() }
            val remainingBoards = newBoards.filter { !it.wins() }
            val newLasWonValue = if(lastWonBoard!=null) nb * lastWonBoard.value() else lastWonValue
            // println("Run for nb: $nb - remain boards: ${remainingBoards.count()} - lastWon: $lastWonValue - newWon: $newLasWonValue")
            when {
                remainingBoards.isEmpty() -> newLasWonValue
                else -> recPart2(newLasWonValue, draw.drop(1), remainingBoards)
            }
        }
    }

    fun resolvePart2Rec(draw: List<Int>, boards: List<BoardRec>): Int = recPart2(0, draw, boards)

    data class Case(val value: Int, var selected: Boolean = false)
    data class CaseRec(val x: Int, val y: Int, val value: Int, val selected: Boolean = false)

    data class BoardRec(val cases: List<CaseRec>) {
        val groupedXCases by lazy { cases.groupBy { it.x } }
        val groupedYCases by lazy { cases.groupBy { it.y } }

        fun play(nb: Int) = BoardRec(
            cases.map { it.copy(selected = if (it.selected) true else it.value == nb) }
        )

        fun wins(): Boolean {
            return (0..4).any { idx ->
                groupedXCases[idx]?.all { it.selected } ?: false ||
                        groupedYCases[idx]?.all { it.selected } ?: false
            }
        }

        fun value() = cases.filter { !it.selected }.sumOf { it.value }

        fun printIt() {
            (0..4).forEach { line ->
                val lineToPrint = cases.filter { it.y == line }.sortedBy { it.x }.map {
                    val padValue = if (it.value < 10) " ${it.value}" else it.value.toString()
                    if (it.selected) red(padValue)
                    else green(padValue)
                }.joinToString("  ")
                println(lineToPrint)
            }
        }
    }

    data class Board(val table: List<List<Case>>) {
        fun addNumber(nb: Int) {
            table.forEach { line ->
                line.forEach { if (nb == it.value) it.selected = true }
            }
        }

        fun wins(): Boolean {
            return try {
                table.forEachIndexed { colIdx, line ->
                    if (line.all { it.selected }) throw Exception("Wining line: $colIdx : $line")
                    line.forEachIndexed { lineIdx, _ ->
                        if (table.map { it[lineIdx] }.all { it.selected }) throw Exception("Wining col: $lineIdx")
                    }
                }
                false
            } catch (e: Exception) {
                true
            }
        }

        fun value() = table.sumOf { s ->
            s.filter { !it.selected }
                .sumOf { it.value }
        }

        fun printIt() {
            table.map { line ->
                line.joinToString("  ") {
                    val padValue = if (it.value < 10) " ${it.value}" else it.value.toString()
                    if (it.selected) red(padValue)
                    else green(padValue)
                }
            }.forEach { println(it) }
        }
    }
}
