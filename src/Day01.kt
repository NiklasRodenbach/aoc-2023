fun main() {
    val stringToNumber = mapOf(
        "one" to "o1e",
        "two" to "t2o",
        "three" to "t3e",
        "four" to "f4r",
        "five" to "f5e",
        "six" to "s6x",
        "seven" to "s7n",
        "eight" to "e8t",
        "nine" to "n9e",
    )

    fun calibrationValue(input: String): Int =
        "${input.first { it.isDigit() }}${input.last { it.isDigit() }}".toInt()

    fun part1(input: List<String>): Int =
        input.sumOf(::calibrationValue)

    fun part2(input: List<String>): Int = input.sumOf { line ->
        var tempString = line
        for (entry in stringToNumber) {
            tempString = tempString.replace(entry.key, entry.value)
        }

        calibrationValue(tempString)
    }

    val testInput = readInput("Day01/Day01_test")
    val testInput2 = readInput("Day01/Day01_test2")

    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01/Day01_input")
    part1(input).println()
    part2(input).println()
}
