fun main() = Day01()

object Day01 : Day(1, false) {
    override fun input() = getInputAndMap { it.toIntOrNull() }.filterNotNull()

    override fun invoke() {
        val input = input()

        part1(input) {
            resolvePart1(input).toString()
        }

        part2(input, "REC") {
            resolvePart2(0, Int.MAX_VALUE, it.toList()).toString()
        }

        part2(input) {
            resolvePart2(it).toString()
        }
    }

    fun resolvePart1(input: List<Int>) = input.fold(Acc(Int.MAX_VALUE, 0)) { acc, curr ->
        Acc(last = curr, counter = acc.counter + if (acc.last < curr) 1 else 0)
    }.counter

    private tailrec fun resolvePart2(counter:Int, prev: Int, list: List<Int>): Int = when {
        list.isEmpty() -> counter
        else -> {
            val curr = list.take(3).sum()
            val addToCounter = if (curr > prev) 1 else 0
            resolvePart2(counter + addToCounter, curr, list.drop(1))
        }
    }

    private fun resolvePart2(list: List<Int>): Int {
        var prevSum = Int.MIN_VALUE

        return(list.indices.first..list.indices.last-3).map {
            val currSum = list.subList(it, it+3).sum()
            (if (currSum > prevSum) 1 else 0).also { prevSum = currSum }
        }.sum()
    }

    private data class Acc(val last: Int, val counter: Int)
}


