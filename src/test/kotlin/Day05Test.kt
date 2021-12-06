import org.junit.jupiter.api.Test
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
}
