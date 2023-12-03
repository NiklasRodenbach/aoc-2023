data class Pos2D(val x: Int, val y: Int)

fun Pos2D.neighbors() =
    listOf(
        this.copy(x = this.x + 1),
        this.copy(x = this.x - 1),
        this.copy(y = this.y + 1),
        this.copy(y = this.y - 1),
        this.copy(x = this.x + 1, y = this.y + 1),
        this.copy(x = this.x - 1, y = this.y - 1),
        this.copy(x = this.x + 1, y = this.y - 1),
        this.copy(x = this.x - 1, y = this.y + 1),
    )

data class Number(val number: Int, val y: Int, val range: IntRange) {
    fun position(): List<Pos2D> =
        buildList {
            range.forEach {
                add(Pos2D(x = it, y))
            }
        }
}

data class Symbol(val x: Int, val y: Int, val symbol: Char) {
    fun position(): Pos2D = Pos2D(x, y)
}

fun main() {
    fun parseInput(input: List<String>): Pair<Set<Symbol>, Set<Number>> {
        val numbers = mutableSetOf<Number>()
        val symbols = mutableSetOf<Symbol>()

        input.forEachIndexed { lineID, line ->
            // parse numbers
            Regex("\\d+").findAll(line).forEach {
                numbers.add(Number(number = it.value.toInt(), y = lineID, range = it.range))
            }

            // parse symbols
            line.forEachIndexed { charID, char ->
                if (char != '.' && !char.isDigit()) {
                    symbols.add(Symbol(x = charID, y = lineID, symbol = char))
                }
            }
        }

        return Pair(symbols, numbers)
    }

    fun part1(input: List<String>): Int {
        val parsed = parseInput(input)

        return parsed.first.map { symbol ->
            parsed.second.filter { number ->
                // check which numbers have a neighbor that is a symbol
                symbol.position().neighbors().any { number.position().contains(it) }
            }
        }.flatten().sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        val parsed = parseInput(input)

        val gears = parsed.first.filter { it.symbol == '*' }

        return gears.map { gear ->
            val numbersOfGear = parsed.second.filter { number ->
                gear.position().neighbors().any { neighbor -> number.position().contains(neighbor) }
            }

            Pair(gear, numbersOfGear)
        }
            .filter { it.second.size == 2 }
            .sumOf { it.second.first().number * it.second.last().number }
    }

    val testInput = readInput("/Day03/Day03_test")

    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("/Day03/Day03_input")
    part1(input).println()
    part2(input).println()
}