fun main() = Day00()

object Day00 : Day(0, testData = true) {
    override fun input() = getInputAndMap { it }

    override fun invoke() {
        val input = input()

        part1(input) { resolvePart1(it) }

        part2(input) { resolvePart2(it) }
    }

    fun resolvePart1(data: List<String>): String {
        TODO()
    }

    fun resolvePart2(data: List<String>): String {
        TODO()
    }
}
