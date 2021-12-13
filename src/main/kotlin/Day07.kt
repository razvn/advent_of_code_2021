import kotlin.math.abs

fun main() = Day07()()

class Day07(test: Boolean = false) : Day(7, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }?.split(",")?.map(String::toInt)
    }.filterNotNull().flatten()

    override fun invoke() {
        val input = input()

        part1(input, "REC") {
            resolvePart1Rec(it).toString()
        }

        part2(input, "REC") {
            resolvePart2Rec(it).toString()
        }
    }

    tailrec fun countIt(acc: Int, data: List<Int>, f: (Int, Int) -> Int, positions: List<Int>): Int = when {
        positions.isEmpty() -> acc
        else -> {
            val idx = positions.first()
            val cost = data.map { f(it,  idx) }.sum()
            val newAcc = if (acc > cost) cost else acc
            // println("calc for $idx: acc: $acc | new: $newAcc, data: $data, pos: $positions")
            countIt(newAcc, data, f, positions.drop(1))
        }
    }

    fun fuel1(start: Int, end: Int) = abs(end - start)
    fun fuel2(start: Int, end: Int) = (0..abs(end - start)).sum()

    fun resolvePart1Rec(input: List<Int>): Int {
        val possiblePositions = ((input.minOrNull()?: 0) .. (input.maxOrNull() ?: 0)).toList()
        return countIt(Int.MAX_VALUE, input, ::fuel1, possiblePositions)
    }

    fun resolvePart2Rec(input: List<Int>): Int {
        val possiblePositions = ((input.minOrNull() ?: 0)..(input.maxOrNull() ?: 0)).toList()
        return countIt(Int.MAX_VALUE, input, ::fuel2, possiblePositions)
    }
}
