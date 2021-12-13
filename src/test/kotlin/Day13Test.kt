import Day13.Direction.LEFT
import Day13.Direction.UP
import Day13.Fold
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day13Test {
    val day = Day13(true)

    @Test
    fun `read input correctly`() {
        assertEquals(day.points.size, 18)
        assertEquals(day.points.first(), Day13.Point(6, 10))
        assertEquals(day.points.last(), Day13.Point(9, 0))

        assertEquals(day.foldings, listOf(Fold(UP, 7), Fold(LEFT, 5)))
    }

    @Test
    fun `print shows correct output`() {
        day.printIt(day.points)
    }

    @Test
    fun `folds correctly`() {
        val newPoints = day.foldIt(Fold(UP, 7), day.points)
        day.printIt(newPoints)
        val finalPoints = day.foldIt(Fold(LEFT, 5), newPoints)
        day.printIt(finalPoints)
    }

    @Test
    fun `resolve part 1`() {
        assertEquals(17, day.resolvePart1(day.points, day.foldings.first()))
    }

    @Test
    fun `resolve part 2`() {
        day.resolvePart2(day.points, day.foldings)
    }
}
