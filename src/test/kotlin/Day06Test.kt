import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day06Test {
    val day = Day06(true)
    val input = day.input()


    @Test
    fun `resolve part 1 rec`() {
        assertEquals(5934, day.resolvePart1Rec(input))
    }

    @Test
    fun `resolve part 2 rec`() {
        assertEquals(26984457539, day.resolvePart2Rec(input))
    }
}
