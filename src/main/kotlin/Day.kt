import kotlin.time.*

abstract class Day(private val number: Int, val testData: Boolean = false, private val testSuffix: String = "") {
    abstract fun input(): List<*>
    fun <T> getInputAndMap(mapFun: (String) -> T): List<T> = Tools.readInput(number, testData, testSuffix).map(mapFun)
    fun getInputAsStrings(): List<String> = Tools.readInput(number, testData, testSuffix)

    @OptIn(ExperimentalTime::class)
    fun <T> part1(input: List<T>, msg: String = "", process: (List<T>) -> String) {
        printResult(1,  msg, measureTimedValue { process(input) })
    }

    @OptIn(ExperimentalTime::class)
    fun <T> part2(input: List<T>, msg: String = "", process: (List<T>) -> String) {
        printResult(2, msg, measureTimedValue { process(input) })
    }

    @OptIn(ExperimentalTime::class)
    private fun printResult(part: Int, msg: String, timedResponse: TimedValue<String>) {
        val colorFun = if (part == 1) Colors::yellow else Colors::green
        println("PART $part $msg: ${colorFun(timedResponse.value)} (${Colors.blue(timedResponse.duration.toDouble(
            DurationUnit.MILLISECONDS
        ))} ms)")
    }

    abstract operator fun invoke()
}
