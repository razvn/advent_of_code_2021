import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day04Test {
    val day = Day04(true)
    val input = day.input()

    @Test
    fun drawIsMappedCorrectly() {
        assertEquals(
            "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
            day.getDraw(input).joinToString(",")
        )
    }

    @Test
    fun bordsAreMappedCorrectly() {
        val s = day.getBoards(input)
        s.forEachIndexed { idx, b ->
            println("Board: $idx")
            b.printIt()
        }
    }

    @Test
    fun `resolve part 1`() {
        val draw = day.getDraw(input)
        val boards = day.getBoards(input)
        assertEquals(4512, day.resolvePart1(draw, boards))
    }

    @Test
    fun `resolve part 1 rec`() {
        val draw = day.getDraw(input)
        val boards = day.getBoardsRec(input)
        assertEquals(4512, day.resolvePart1Rec(draw, boards))
    }


    @Test
    fun `resolve part 2`() {
        val draw = day.getDraw(input)
        val boards = day.getBoards(input)
        assertEquals(1924, day.resolvePart2(draw, boards))
    }

    @Test
    fun `resolve part 2 rec`() {
        val draw = day.getDraw(input)
        val boards = day.getBoardsRec(input)
        assertEquals(1924, day.resolvePart2Rec(draw, boards))
    }
}
