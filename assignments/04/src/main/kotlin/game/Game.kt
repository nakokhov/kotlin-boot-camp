package game

import java.util.Random

class Game(val Words: List<String>) {
    var currentWord: String = ""
    val STEPS = 10
    fun run() {
        println("Welcome to Bulls and Cows game!")
        var isFinished = false
        while (!isFinished) {
            currentWord = Words[Random().nextInt(Words.size - 1)]
            println("I offered a ${currentWord.length}-letter word, your guess?")
            print("> ")
            for (i in 0..STEPS) {
                val input = readLine() ?: ""
                if (input == currentWord) {
                    println("You won!")
                    isFinished = true
                    break
                }
                val (cows, bulls) = getBullsAndCows(input)
                println("Bulls: $bulls")
                println("Cows: $cows")
            }
            if (!isFinished) {
                println("You lose!")
            }
            println("Wanna play again? Y/N")
            print("> ")
            isFinished = (readLine() == "N")
        }
    }

    private fun getBullsAndCows(word: String): Pair<Int, Int> {
        return word.foldIndexed(Pair(0, 0)) { i, bc, ch ->
            Pair(bc.first + if (ch == currentWord[i]) 1 else 0,
                    bc.second + if (ch != currentWord[i] && ch in currentWord) 1 else 0)
        }
    }
}