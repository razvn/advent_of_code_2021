import org.junit.jupiter.api.Test
import java.lang.Math.abs
import kotlin.test.assertEquals

internal class Day05Test {
    val day = Day05(true)
    val input = day.input()

    @Test
    fun `resolve part 1`() {
        assertEquals(5, day.resolvePart1(input))
    }

    @Test
    fun `resolve part 1 rec`() {
        assertEquals(5, day.resolvePart1Rec(input))
    }

    @Test
    fun `resolve part 2`() {
        assertEquals(12, day.resolvePart2(input))
    }

    @Test
    fun `resolve part 2 rec`() {
        assertEquals(12, day.resolvePart2Rec(input))
    }

    @Test
    fun rangeTest() {
        val l = Line(Point(9, 4), Point(3,4))
        println(l)
        println( "H: ${l.isH()} V: ${l.isV()} HorV: ${l.isHorV()} Diag: ${l.isDiag()}")
        l.points.forEach { println(it) }
        println("maxY: ${l.maxY()} - minY: ${l.minY()}")
    }
}
