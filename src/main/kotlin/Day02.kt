fun main() = Day02()()

class Day02(test: Boolean = false) : Day(2, test) {
    val input = getInputData {
        Order.fromInput(it)
    }.filterNotNull()

    override fun invoke() {
        part1(input) {
            part1Resolve(it).toString()
        }

        part2(input) {
            part2Resolve(it).toString()
        }
    }

    fun part1Resolve(data: List<Order>) = data.fold(Position(0, 0)) { acc, order ->
        acc.moveBy(order.value, order.direction)
    }.let { it.horizontal * it.depth }

    fun part2Resolve(data: List<Order>) = data.fold(Position(0, 0)) { acc, order ->
        acc.moveByAim(order.value, order.direction)
    }.let { it.horizontal * it.depth }
}


private data class Position(val horizontal: Int, val depth: Int, val aim: Int = 0) {
    fun moveBy(value: Int, direction: Order.Direction) = Position(
        direction.horizFactor * value + this.horizontal,
        direction.depthFactor * value + this.depth
    )

    fun moveByAim(value: Int, direction: Order.Direction) = Position(
        direction.horizFactor * value + this.horizontal,
        direction.horizFactor * aim * value + this.depth,
        direction.depthFactor * value + this.aim
    )
}

data class Order(val direction: Direction, val value: Int) {
    enum class Direction(val horizFactor: Int, val depthFactor: Int) {
        UP(0, -1),
        DOWN(0, 1),
        FORWARD(1, 0)
    }

    companion object {
        fun fromInput(input: String): Order? =
            input.split(" ")
                .takeIf { it.size == 2 }
                ?.let {
                    Order(Direction.valueOf(it.first().uppercase()), it.last().toInt())
                }
    }
}

