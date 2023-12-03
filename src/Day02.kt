
fun main() {
    data class Set(val red: Int?, val green: Int?, val blue: Int?)
    data class Game(val id: Int, val sets: List<Set>)

    fun parseGame(input: String): Game {
        val (game, sets) = input.split(":")

        val gameID = game.split(" ")[1].toInt()

        val parsedSets = sets.split(";").map { set ->
            val colors = set.trim().split(",")

            val red = colors.find { it.contains("red")}?.replace("red", "")?.trim()?.toInt() ?: 0
            val green = colors.find { it.contains("green")}?.replace("green", "")?.trim()?.toInt() ?: 0
            val blue = colors.find { it.contains("blue")}?.replace("blue", "")?.trim()?.toInt() ?: 0

            Set(red, green, blue)
        }

        return Game(gameID, parsedSets)
    }


    fun part1(input: List<String>): Int {
        val red = 12
        val green = 13
        val blue = 14

        return input.map(::parseGame).filter { game ->
            game.sets.none { set ->
                set.red!! > red || set.green!! > green || set.blue!! > blue
            }
        }.sumOf { it.id }
    }
    
    fun part2(input: List<String>): Int {
        return input.map(::parseGame).sumOf { game ->
            val maxRed = game.sets.maxOf { it.red ?: 0 }
            val maxGreen = game.sets.maxOf { it.green ?: 0 }
            val maxBlue = game.sets.maxOf { it.blue ?: 0 }

            maxRed * maxGreen * maxBlue
        }
    }

    val testInput = readInput("Day02/Day02_test")

    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02/Day02_input")
    part1(input).println()
    part2(input).println()
}
