package eightbitlab.server

import eightbitlab.server.protocol.Error
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.ServerSocket
import java.util.*

class Server(address: String, port: Int) {
    internal lateinit var serverSocket: ServerSocket
    internal val channelsMap = HashMap<String, Channel>()
    internal val connectionListeningThread = ConnectionListeningThread()
    internal val commandParser = CommandParser()

    init {
        try {
            serverSocket = ServerSocket(port, 50, InetAddress.getByName(address))
            connectionListeningThread.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun close() {
        try {
            connectionListeningThread.interrupt()
            serverSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    inner class ConnectionListeningThread : Thread() {

        override fun run() {
            while (!interrupted()) {
                val clientSocket = serverSocket.accept()
                Log.print("$clientSocket connected")
                val writer = Writer(clientSocket)
                val client = Client(clientSocket, writer)
                ReaderThread(client).start()
            }
        }

    }

    inner class ReaderThread(val client: Client) : Thread() {

        override fun run() {
            val reader = BufferedReader(InputStreamReader(client.socket.inputStream))

            try {
                awaitInput(reader)
            } catch (e: Exception) {
                Log.print("Input closed for $client")
            } finally {
                exitChannel()
                Log.print("$client disconnected")
            }
        }

        @Throws(IOException::class)
        private fun awaitInput(reader: BufferedReader) {
            reader.lines()
                    .map { it.trim().toUpperCase() }
                    .map {
                        try {
                            Log.print("Server received $it")
                            return@map commandParser.parse(it)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            client.sendMessage(Error.INVALID_COMMAND)
                            return@map commandParser.empty()
                        }
                    }
                    .forEach {
                        val command = it.first
                        val argument = it.second
                        try {
                            command.execute(argument, this@Server, client)
                        } catch (e: Exception) {
                            Log.error("Error on executing $command $argument")
                        }
                    }
        }

        private fun exitChannel() {
            channelsMap[client.currentChannel?.name]?.remove(client)
        }

    }
}