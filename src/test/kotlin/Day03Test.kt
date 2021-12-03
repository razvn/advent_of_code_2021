import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day03Test {
    val day = Day03(true)
    val input = day.input()

    @Test
    fun `resolve part 1`() {
        assertEquals(198, day.resolvePart1(input))
    }

    @Test
    fun `rec resolve part 1`() {
        assertEquals(198, day.recPart1(input))
    }

    @Test
    fun `resolve part 2`() {
        assertEquals(230, day.resolvePart2(input))
    }

    @Test
    fun `rec resolve part 2`() {
        assertEquals(230, day.recPart2(input))
    }
}
