fun main() = Day06()()

class Day06(test: Boolean = false) : Day(6, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
    }.filterNotNull()
        .flatMap { it.split(",").map(String::toInt) }

    override fun invoke() {
        val input = input()

        part1(input, "REC") {
            resolvePart1Rec(it).toString()
        }

        part2(input, "REC") {
            resolvePart2Rec(it).toString()
        }
    }

    tailrec fun recPart1(days: Int, acc: Int, input: Map<Int,Long>): Long = when {
        acc == days -> input.values.sum()
        else -> {
            // println("$acc :" + input)
            val newAcc = acc + 1
            val new = input[0] ?: 0
            val newMap = input.map { (k, v) -> (if (k > 0) k - 1 else k) to v }.toMutableList().toMap().toMutableMap()
            newMap[6] = (newMap[6] ?: 0) + new
            newMap[8] = new

            recPart1(days, newAcc, newMap)
        }
    }

    fun resolvePart1Rec(input: List<Int>): Long =
        recPart1(80, 0, (0..8).map { nb -> nb to input.count { it == nb }.toLong()}.toMap() )

    fun resolvePart2Rec(input: List<Int>): Long =
        recPart1(256, 0, (0..8).map { nb -> nb to input.count { it == nb }.toLong()}.toMap() )
}
