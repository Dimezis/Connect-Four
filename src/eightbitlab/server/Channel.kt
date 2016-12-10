package eightbitlab.server

import eightbitlab.game.ConnectFourGame
import eightbitlab.game.ConnectFourGame.Companion.RED
import eightbitlab.game.ConnectFourGame.Companion.YELLOW
import eightbitlab.server.protocol.Command
import eightbitlab.server.protocol.Error
import eightbitlab.server.protocol.Message
import eightbitlab.swapKeys
import java.util.concurrent.ConcurrentHashMap

//TODO Channel is a bit over-complicated
//TODO list of names

/**
 * Represents a channel for 2 clients to play with each other
 * 1 who joins takes RED color
 * 1 move is by RED
 *
 * After each round colors are swapped
 */
class Channel(val name: String) {
    private val maxRounds = 4
    private val playersMap = ConcurrentHashMap<Char, Client?>()

    private var game: ConnectFourGame = ConnectFourGame()
    private var roundNumber = 1 //not sure if it should be here

    fun join(client: Client) {
        synchronized(this, {
            if (client.currentChannel != null) {
                client.sendMessage(Error.ALREADY_IN_CHANNEL)
                return
            }

            findPlaceForPlayer(client)
            client.currentChannel = this
            if (!hasPlaces()) {
                createGame()
            }
        })
    }

    private fun findPlaceForPlayer(client: Client) {
        if (!playersMap.containsKey(RED)) {
            playersMap[RED] = client
            client.currentColor = RED
        } else if (!playersMap.containsKey(YELLOW)) {
            playersMap[YELLOW] = client
            client.currentColor = YELLOW
        } else {
            throw IllegalStateException(Error.CHANNEL_IS_FULL.message())
        }
        client.sendMessage(Message.SUCCESS)
    }

    private fun createGame() {
        game = ConnectFourGame()
        playersMap.entries.forEach {
            it.value?.sendMessage(Message.NEW_GAME)
        }
        game.onGameEnd = onGameEnd()
        game.onMove = {
            val opponent = getOpponentOf(playersMap[it])
            notifyAboutMove(opponent)
        }

        notifyAboutMove(playersMap[RED])
    }

    private fun onGameEnd(): (Char?) -> Unit {
        return { winner ->
            if (winner == null) {
                onDraw()
            } else {
                onWin(winner)
            }

            //opponents need to play 4 rounds
            if (roundNumber < maxRounds) {
                swapColors()
                createGame()
                roundNumber++
            } else {
                clearChannel()
            }
        }
    }

    private fun onWin(winner: Char) {
        val winnerClient = playersMap[winner] ?: return
        val opponent = getOpponentOf(winnerClient)

        opponent?.sendMessage(Message.YOU_LOST)
        opponent?.name?.let { Statistics.lose(it) }

        winnerClient.sendMessage(Message.YOU_WON)
        winnerClient.name?.let { Statistics.win(it) }

        Log.print("Player $winnerClient won")
    }

    private fun onDraw() {
        playersMap.values.forEach {
            it?.sendMessage(Message.DRAW)
            it?.name?.let { it -> Statistics.draw(it) }
        }
        Log.print(Message.DRAW.name)
    }

    private fun swapColors() {
        playersMap.swapKeys(RED, YELLOW)
        playersMap.entries.forEach {
            it.value?.currentColor = it.key
        }
    }

    fun getOpponentOf(client: Client?): Client? {
        playersMap.entries.forEach {
            if (client != it.value) {
                return it.value
            }
        }
        return null
    }

    private val amountOfPlayersNeeded = 2

    fun put(client: Client, column: Int) {
        synchronized(this, {
            if (!playersMap.containsValue(client)) {
                throw RuntimeException("Client $client is not connected to channel $this, wtf?")
            }
            if (playersMap.size < amountOfPlayersNeeded) {
                throw IllegalStateException(Error.NOT_YOUR_TURN.message())
            }

            //notify another client about move
            val opponent = getOpponentOf(client)
            opponent?.sendMessage("${Command.PUT} $column")
            game.put(client.currentColor!!, column)

            printGameInfo()
        })
    }

    private fun printGameInfo() {
        Log.print("\nChannel $name (round $roundNumber)\nMove ${game.moveCount}\n")
        Log.print(game.getBoardAsString())
    }

    private fun notifyAboutMove(client: Client?) {
        client?.sendMessage(Message.YOUR_MOVE)
    }

    fun remove(player: Client) {
        synchronized(this, {
            playersMap.remove(player.currentColor)
            player.currentChannel = null
            player.currentColor = null
            resetRoundCounter()
        })
    }

    private fun resetRoundCounter() {
        roundNumber = 1
    }

    fun clearChannel() {
        synchronized(this, {
            playersMap.values.forEach {
                it?.closeSilently()
                it?.currentChannel = null
                it?.currentColor = null
            }
            playersMap.clear()
            resetRoundCounter()
        })
    }

    fun hasPlaces() = synchronized(this, { playersMap.size < amountOfPlayersNeeded })

    override fun toString(): String {
        return "Channel(name='$name', playersMap=$playersMap)"
    }
}