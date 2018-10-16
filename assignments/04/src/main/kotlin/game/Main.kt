package game

fun loadResource(resource: String): String =
        try {
            object {}.javaClass.getResource(resource)
                    .readText(Charsets.UTF_8)
        } catch (all: Exception) {
            throw RuntimeException("Failed to load resource=$resource!", all)
        }

fun main(args: Array<String>) {
    val game = Game(loadResource("/dictionary.txt").split("\n"))
    game.run()
}