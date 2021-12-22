fun main() = Day10()()

class Day10(test: Boolean = false) : Day(10, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
    }.filterNotNull()

    override fun invoke() {
        val input = input()

        part1(input) {
            resolvePart1(it).toString()
        }

        part2(input) {
            resolvePart2(it).toString()
        }
    }


    @JvmInline
    value class Chuck(val value: String) {

    }

    fun getErrorValue(char: Char): Int {
        return when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    fun getClosingError(line: String): Char? {
        val map = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>',
        )
        val validOpen = map.keys
        val validClose = map.values.toSet()

        val openings = mutableListOf<Char>()

        line.forEachIndexed { idx, ch ->
            // println("$idx -> $ch")
            if (ch in validOpen) openings += ch
            else {
                val lastOpen = openings.last()
                // println("\t$ch == ${map[lastOpen]} ($lastOpen) -> ${ch == map[lastOpen]}")
                if (ch == map[lastOpen]) {
                    // println("\tBefore removing $openings")
                    openings.removeLast()
                    // println("\tAfter removing $openings")
                } else {
                    // println("\tlast: ${lastOpen} - close: ${map[lastOpen]}- openings: $openings")
                    return ch
                }
            }
            // println("\tend with - openings: $openings")
        }
        return null
    }

    fun getOpensNotClosed(line: String): List<Char> {
        val map = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>',
        )
        val validOpen = map.keys

        val openings = mutableListOf<Char>()

        line.forEachIndexed { idx, ch ->
            // println("$idx -> $ch")
            if (ch in validOpen) openings += ch
            else {
                val lastOpen = openings.last()
                // println("\t$ch == ${map[lastOpen]} ($lastOpen) -> ${ch == map[lastOpen]}")
                if (ch == map[lastOpen]) {
                    // println("\tBefore removing $openings")
                    openings.removeLast()
                    // println("\tAfter removing $openings")
                }
            }
            // println("\tend with - openings: $openings")
        }
        return openings
    }

    fun resolvePart1(input: List<String>): Int {
        return input.mapNotNull {
            getClosingError(it)
        }.also { println("Errors: $it") }
            .map {
                getErrorValue(it)
            }
            .also { println("Values: $it") }
            .sum()
    }

    fun openToClosed(opens: List<Char>): List<Char> {
        val map = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>',
        )

        return opens.reversed().mapNotNull { map[it] }
    }

    fun calcData(input: List<Char>): Long {
        val map = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )

        return input.mapNotNull { map[it] }.fold(0L) {acc, i ->
            acc * 5 + i
        }
    }

    fun resolvePart2(input: List<String>): Long {
        return input.filter { getClosingError(it) == null } //.also {  it.map { println(it) } }
            .map { getOpensNotClosed(it) } //.also { it.map { println(it.joinToString("")) } }
            .map { openToClosed(it) }// .also {  it.map { println(it.joinToString("")) } }
            .map { calcData(it) }
            .sorted() //.also {  it.map { println(it) } }
            .let { it[it.count() / 2] }
    }
}
