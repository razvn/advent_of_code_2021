import Colors.green
import Colors.red

fun main() = Day04()()

class Day04(test: Boolean = false) : Day(4, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
    }.filterNotNull()

    fun getDraw(input: List<String>) = input.first().split(",").map(String::toInt)

    data class Case(val value: Int, var selected: Boolean = false)

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
            table.map {
                it.joinToString() {
                    if (it.selected) red(it.value.toString())
                    else green(it.value.toString())
                }
            }.forEach { println(it) }
        }
    }

    fun getBoards(input: List<String>) = input.drop(1)
        .map {
            it.split(" ")
                .filter { it.isNotBlank() }
                .map { Case(it.trim().toInt()) }
        }
        .chunked(5)
        .map { Board(it) }

    override fun invoke() {
        val input = input()
        val draw = getDraw(input)
        val boards = getBoards(input)

        part1(input) {
            resolvePart1(draw, boards).toString()
        }

        part2(input) {
            resolvePart2(draw, boards).toString()
        }
    }

    fun resolvePart1(draw: List<Int>, boards: List<Board>): Int {
        draw.forEach { nb ->
            boards.forEach { board ->
                board.addNumber(nb)
                if (board.wins()) return board.value() * nb
            }
        }
        return 0
    }

    fun resolvePart2(draw: List<Int>, boards: List<Board>): Int {
        draw.forEach { nb ->
            boards.forEach { board ->
                board.addNumber(nb)
                if (boards.all { it.wins() }) return board.value() * nb
            }
        }
        return 0
    }
}
