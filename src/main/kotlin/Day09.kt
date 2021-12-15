fun main() = Day09()()

class Day09(test: Boolean = false) : Day(9, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }?.chunked(1)?.map(String::toInt)
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

    data class Point(val x: Int, val y: Int, val value: Int) {
        fun getNeighbours(data: List<Point>) = data.filter {
            (it.x == this.x - 1 && it.y == this.y) ||
                    (it.x == this.x && it.y == this.y - 1) ||
                    (it.x == this.x + 1 && it.y == this.y) ||
                    (it.x == this.x && it.y == this.y + 1)
        }.toSet()

        fun getNeighboursLessThan9(data: List<Point>) = this.getNeighbours(data).filter { it.value < 9 }.toSet()

        fun isLow(data: List<Point>): Boolean {
            // println("--- calc for: $this")
            val neighbours = getNeighbours(data)

            // println("--- neighbours: $neighbours")
            return neighbours.all { it.value > this.value }
        }

        tailrec fun grow(data: List<Point>, current: Set<Point>, new: Set<Point>): Set<Point> = when {
            new.isEmpty() -> current
            else -> {
                val newCurrent = current + new
                val newNew = new.flatMap { it.getNeighboursLessThan9(data) }.toSet()
                grow(data, newCurrent, newNew.filter { it !in newCurrent }.toSet())
            }
        }

        fun bassin(points: List<Point>): Set<Point> {
            return grow(points, setOf(this), getNeighboursLessThan9(points))
        }
    }

    fun resolvePart1(input: List<List<Int>>): Int {
        val points = input.mapIndexed { yIdx, values ->
            values.mapIndexed { xIdx, i ->
                Point(xIdx, yIdx, i)
            }
        }.flatten()
        val lowPoints = points.filter { it.isLow(points) }
        return lowPoints.map { it.value + 1 }.sum()
    }

    fun resolvePart2(input: List<List<Int>>): Int {
        val points = input.mapIndexed { yIdx, values ->
            values.mapIndexed { xIdx, i ->
                Point(xIdx, yIdx, i)
            }
        }.flatten()
        val lowPoints = points.filter { it.isLow(points) }
        val bassins = lowPoints.map { it.bassin(points) }
        val bassinValues = bassins.map { it.count() }.sortedDescending()
        return bassinValues.take(3).reduce { a, v -> a * v }
    }
}
