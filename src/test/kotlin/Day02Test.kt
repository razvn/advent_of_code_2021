import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day02Test {
    val day = Day02(true)

    @Test
    fun part1Resolve() {
        assertEquals(150, day.part1Resolve(day.input))
    }

    @Test
    fun part2Resolve() {
        assertEquals(900, day.part2Resolve(day.input))
    }
}
