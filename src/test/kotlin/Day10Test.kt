import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day10Test {
    val day = Day10(true)
    val input = day.input()

    @Test
    fun `error tests`() {
        assertEquals('}', day.getClosingError("{([(<{}[<>[]}>{[]{[(<()>"))
        assertEquals(')', day.getClosingError("[[<[([]))<([[{}[[()]]]"))
        assertEquals(']', day.getClosingError("[{[{({}]{}}([{[{{{}}([]"))
        assertEquals(')', day.getClosingError("[<(<(<(<{}))><([]([]()"))
        assertEquals('>', day.getClosingError("<{([([[(<>()){}]>(<<{{"))
        assertEquals(null, day.getClosingError("[({(<(())[]>[[{[]{<()<>>"))
        assertEquals(null, day.getClosingError("[(()[<>])]({[<{<<[]>>("))
    }

    @Test
    fun `resolve part 1`() {
        assertEquals(26397, day.resolvePart1(input))
    }

    @Test
    fun `resolve part 2 rec`() {
        assertEquals(288957, day.resolvePart2(input))
    }
}
