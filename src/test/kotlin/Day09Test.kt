import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day09Test {
    val day = Day09(true)
    val input = day.input()

    @Test
    fun `resolve part 1`() {
        assertEquals(15, day.resolvePart1(input))
    }

    @Test
    fun `resolve part 2 rec`() {
        assertEquals(1134, day.resolvePart2(input))
    }
}
