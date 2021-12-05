import Colors.green
import Colors.red
import java.lang.Integer.max
import java.lang.Integer.min
import java.lang.Math.abs

fun main() = Day05()()

class Day05(test: Boolean = false) : Day(5, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
            ?.split(" -> ")
            ?.map { Point.fromString(it.trim()) }
    }.filterNotNull()
        .map {
            Line(it[0], it[1])
        }

    override fun invoke() {
        val input = input()

        part1(input) {
            resolvePart1(it).toString()
        }

        part1(input, "REC") {
            resolvePart1Rec(it).toString()
        }

        part2(input) {
            resolvePart2(it).toString()
        }

        part2(input, "REC") {
            resolvePart2(it).toString()
        }
    }

    fun resolvePart1(input: List<Line>): Int {
        val filterHV = input
            .filter { it.isHorV() }
        val points = filterHV
            .flatMap { it.points }
            .groupBy { it }.mapValues { it.value.count() }

        // printPoints(filterHV.maxOf { it.maxX() } + 1, filterHV.maxOf { it.maxY() } + 1, points)
        return points.count { it.value > 1 }
    }

    tailrec fun recPart1(acc: Set<Point>, input: List<Line>): Int = when {
        input.isEmpty() -> acc.count()
        else -> {
            val currLine = input.first()
            val restInput = input.drop(1)
            val communPoints = restInput.flatMap { it.intersect(currLine) }.toSet()
            val newAcc = acc + communPoints
            recPart1(newAcc, restInput)
        }
    }

    fun resolvePart1Rec(input: List<Line>): Int =
        recPart1(emptySet(), input.filter(Line::isHorV))


    fun resolvePart2(input: List<Line>): Int {
        val filterHVDiag = input
            .filter { it.isHorV() || it.isDiag() }

        val points = filterHVDiag
            .flatMap { it.points }.groupBy { it }.mapValues { it.value.count() }
        // printPoints(filterHVDiag.maxOf { it.maxX() } + 1, filterHVDiag.maxOf { it.maxY() } + 1, points)
        return points.count { it.value > 1 }
    }

    fun resolvePart2Rec(input: List<Line>): Int {
        return recPart1(emptySet(), input)
    }

    private fun printPoints(lenX: Int, lenY: Int, points: Map<Point, Int>) {
        val maxValueLen = points.maxOf { it.value }.toString().length
        val s = (0 until lenY).map {
            buildList {
                (0 until lenX).forEach {
                    add(".".padStart(maxValueLen - 1))
                }
            }.toMutableList()
        }

        points.forEach {
            s[it.key.y][it.key.x] = it.value.let {
                val str = it.toString().let {
                    it.padStart(maxValueLen - it.length)
                }
                if (it < 2) green(str) else red(str)
            }
        }
        s.forEach { println(it.joinToString(" ")) }
    }
}

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromString(s: String) = s.split(",")
            .map(String::toInt)
            .let { Point(it[0], it[1]) }
    }
}

data class Line(val start: Point, val end: Point) {
    val points by lazy {
        buildSet {
            if (isH()) (minX()..maxX()).forEach { add(Point(it, start.y)) }
            if (isV()) (minY()..maxY()).forEach { add(Point(start.x, it)) }
            if (isDiag()) {
                val xDir = if (start.x < end.x) 1 else -1
                val yDir = if (start.y < end.y) 1 else -1
                val len = abs(start.x - end.x)
                (0..len).forEach {
                    add(Point(start.x + (xDir * it), start.y + (yDir * it)))
                }
            }
        }
    }

    fun isH() = start.y == end.y
    fun isV() = start.x == end.x
    fun isHorV() = isH() || isV()
    fun isDiag() = abs(start.x - end.x) == abs(start.y - end.y)

    fun intersect(l: Line) = this.points.filter { l.points.contains(it) }

    fun maxX() = max(start.x, end.x)
    fun maxY() = max(start.y, end.y)
    fun minX() = min(start.x, end.x)
    fun minY() = min(start.y, end.y)
}
