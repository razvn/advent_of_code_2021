import Colors.green
import Colors.red
import Day13.Direction.LEFT
import Day13.Direction.UP
import kotlin.math.abs

fun main() = Day13()()

class Day13(test: Boolean = false) : Day(13, test) {
    override fun input() = getInputAndMap {
        it.takeIf { it.isNotBlank() }
    }.filterNotNull()

    val points = input().filterNot { it.startsWith("fold") }.map(Point::parse).toSet()
    val foldings = input().filter { it.startsWith("fold") }.map(Fold::parse)

    fun printIt(points: Set<Point>) {
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }

        val board = (0..maxY).map {
            buildList {
                (0..maxX).forEach { add(green(".")) }
            }.toMutableList()
        }

        points.forEach {
            board[it.y][it.x] = red("#")
        }

        board.forEach {
            println(it.joinToString(""))
        }
    }

    override fun invoke() {
        val input = input()
        part1(input) {
            resolvePart1(points, foldings.first()).toString()
        }

        part2(input, "REC") {
            resolvePart2(points, foldings)
        }
    }

    fun resolvePart1(input: Set<Point>, fold: Fold): Int {
        return foldIt(fold, input).count()
    }

    tailrec fun rec(points: Set<Point>, foldList: List<Fold>): Set<Point> = when {
        foldList.isEmpty() -> points
        else -> {
            val curr = foldList.first()
            val restFold = foldList.drop(1)
            val newPoints = foldIt(curr, points)
            rec(newPoints, restFold)
        }
    }

    fun resolvePart2(input: Set<Point>, foldList: List<Fold>): String {
        printIt(rec(input, foldList))
        return ""
    }

    fun foldIt(fold: Fold, points: Set<Point>): Set<Point> {
        val pointsToMove = points.filter {
            when (fold.dir) {
                UP -> it.y > fold.value
                LEFT -> it.x > fold.value
            }
        }

        val pointsDontMove = points.filter {
            when (fold.dir) {
                UP -> it.y <= fold.value
                LEFT -> it.x <= fold.value
            }
        }

        return (pointsDontMove + pointsToMove.map { p ->
            when (fold.dir) {
                UP -> p.copy(y = abs(fold.value * 2 - p.y))
                LEFT -> p.copy(x = abs(fold.value * 2 - p.x))
            }
        }).toSet()
    }

    data class Point(val x: Int, val y: Int) {
        companion object {
            fun parse(s: String) = s.split(",").map(String::toInt).let {
                Point(it.first(), it.last())
            }
        }
    }

    enum class Direction {
        UP, LEFT;

        companion object {
            fun parse(s: String) = when (s) {
                "y" -> UP
                "x" -> LEFT
                else -> throw Exception("Bad direction: $s")
            }
        }
    }

    data class Fold(val dir: Direction, val value: Int) {
        companion object {
            fun parse(s: String) = s.removePrefix("fold along ").split("=").let {
                Fold(Direction.parse(it.first()), it.last().toInt())
            }
        }
    }
}
