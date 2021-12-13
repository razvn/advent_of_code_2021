import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day07Test {
    val day = Day07(true)
    val input = day.input()

    @Test
    fun `fuel1 counts correctly`() {
        assertEquals(14, day.fuel1(16, 2))
        assertEquals(0, day.fuel1(2, 2))
        assertEquals(2, day.fuel1(0, 2))
    }

    @Test
    fun `resolve part 1 rec`() {
        assertEquals(37, day.resolvePart1Rec(input))
    }

    @Test
    fun `resolve part 2 rec`() {
        assertEquals(12, day.resolvePart2Rec(input))
    }
}
