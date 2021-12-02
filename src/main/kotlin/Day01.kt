fun main() = Day01()

object Day01 : Day(1) {
    private val input = getInputData { it.toIntOrNull() }.filterNotNull()
    override fun invoke() {
        data class Acc(val last: Int, val counter: Int)
        part1(input) {
            val result = it.fold(Acc(Int.MAX_VALUE, 0)) { acc, curr ->
                Acc(last = curr, counter = acc.counter + if (acc.last < curr) 1 else 0)
            }
            result.counter.toString()
        }

        part2(input) {
            countByThreeRec(0, Int.MAX_VALUE, it.toList()).toString()
            // countByThree(it).toString()
        }
    }

    private tailrec fun countByThreeRec(counter:Int, prev: Int, list: List<Int>): Int = when {
        list.isEmpty() -> counter
        else -> {
            val curr = list.take(3).sum()
            val addToCounter = if (curr > prev) 1 else 0
            countByThreeRec(counter + addToCounter, curr, list.drop(1))
        }
    }

    private fun countByThree(list: List<Int>): Int {
        var prevSum = Int.MIN_VALUE

        return(list.indices.first..list.indices.last-3).map {
            val currSum = list.subList(it, it+3).sum()
            (if (currSum > prevSum) 1 else 0).also { prevSum = currSum }
        }.sum()
    }
}
