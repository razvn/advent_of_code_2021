import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day02Test {
    val day = Day02(true)
    val input = day.input()

    @Test
    fun part1Resolve() {
        assertEquals(150, day.resolvePart1(input))
    }

    @Test
    fun part2Resolve() {
        assertEquals(900, day.resolvePart2(input))
    }
}
