fun main() = Day03()()

class Day03(test: Boolean = false) : Day(3, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
    }.filterNotNull()

    override fun invoke() {
        val input = input()

        part1(input, "REC") {
            recPart1(it).toString()
        }

        part1(input) {
            resolvePart1(it).toString()
        }

        part2(input, "REC") {
            recPart2(it).toString()
        }

        part2(input) {
            resolvePart2(it).toString()
        }
    }

    fun resolvePart1(data: List<String>): Int {
        val gamma = mutableListOf<Char>()
        val epsilon = mutableListOf<Char>()
        data.first().indices.forEach { idx ->
            val mostCommun = getMostCommun(data, idx)
            val lessCommun = mostCommun.opposite()
            gamma.add(mostCommun)
            epsilon.add(lessCommun)
        }

        val gRate = gamma.joinToString("").toInt(2)
        val eRate = epsilon.joinToString("").toInt(2)

        return gRate * eRate
    }

    fun recPart1(data: List<String>) = rec1(Acc(), 0, data.first().length, data)

    private tailrec fun rec1(acc: Acc, idx: Int, max: Int, data: List<String>): Int = when {
        idx >= max -> acc.result()
        else -> {
            val mostCommun = getMostCommun(data, idx)
            rec1(
                acc.copy(gamma = acc.gamma + mostCommun, epsilon = acc.epsilon + mostCommun.opposite()),
                idx + 1,
                max,
                data
            )
        }
    }

    data class Acc(val gamma: String = "", val epsilon: String = "") {
        fun result() = this.gamma.toInt(2) * this.epsilon.toInt(2)
    }

    fun resolvePart2(data: List<String>): Int {
        var currentO = data
        var currentCO2 = data
        var idx = 0
        while (currentO.size != 1) {
            val mostCommun = getMostCommun(currentO, idx)
            currentO = currentO.filter { it[idx] == mostCommun }
            idx++
        }

        idx = 0
        while (currentCO2.size != 1) {
            val lessCommun = getLessCommun(currentCO2, idx)
            currentCO2 = currentCO2.filter { it[idx] == lessCommun }
            idx++
        }

        val ratingO = currentO.first().toInt(2)
        val ratingCO2 = currentCO2.first().toInt(2)
        return ratingO * ratingCO2
    }

    private fun getMostCommun(data: List<String>, idx: Int): Char {
        val nbByChar = data
            .groupingBy { it[idx] }
            .eachCount()

        return when (nbByChar['1']) {
            nbByChar['0'] -> '1'
            else -> nbByChar.maxByOrNull { it.value }?.key ?: '1'
        }
    }

    fun recPart2(data: List<String>): Int {
        val oxygen = findLastOne(0, data, ::getMostCommun)
        val co2 = findLastOne(0, data, ::getLessCommun)

        return oxygen * co2
    }

    private tailrec fun findLastOne(idx: Int, data: List<String>, f: (List<String>, Int) -> Char): Int =
        when (data.size) {
            1 -> data.first().toInt(2)
            else -> findLastOne(idx + 1, data.filter { it[idx] == f(data, idx) }, f)
        }

    private fun getLessCommun(data: List<String>, idx: Int) = getMostCommun(data, idx).opposite()

    private fun Char.opposite() = if (this == '1') '0' else '1'
}
