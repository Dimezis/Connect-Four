package eightbitlab.server

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.Executors

//TODO optimize
object Statistics {

    private val playersMap = HashMap<String, Data>()
    private val executor = Executors.newSingleThreadExecutor()
    private val logFileName = "./statistics.txt"

    private fun getItem(playerName: String): Data {
        var item = playersMap[playerName]
        if (item == null) {
            item = Data()
            item.name = playerName
            playersMap[playerName] = item
        }
        return item
    }

    fun win(playerName: String) {
        synchronized(this, {
            getItem(playerName).wins++
            logToFile()
        })
    }

    fun lose(playerName: String) {
        synchronized(this, {
            getItem(playerName).losses++
            logToFile()
        })
    }

    fun draw(playerName: String) {
        synchronized(this, {
            getItem(playerName).draws++
            logToFile()
        })
    }

    private fun logToFile() {
        executor.execute {
            val listRepresentation = playersMap
                    .values
                    .sortedBy { it.wins - it.losses }
                    .map {
                        with(it) { "Name: $name\nWins: $wins\nLosses: $losses\nDraws: $draws\n\n" }
                    }
            Files.write(Paths.get(logFileName), listRepresentation, StandardCharsets.UTF_8)
        }
    }

    private class Data {
        var name: String = ""
        var wins: Int = 0
        var losses: Int = 0
        var draws: Int = 0
    }

}