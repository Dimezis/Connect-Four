package eightbitlab.server

import eightbitlab.server.protocol.Error
import eightbitlab.server.protocol.Message
import java.net.Socket

class Client(val socket: Socket, private val writer: Writer) {

    var currentChannel: Channel? = null
    var currentColor: Char? = null
    var name: String? = null

    fun sendMessage(message: String): Client {
        logMessage(message)
        writer.write(message)
        return this
    }

    fun sendMessage(message: Message): Client {
        logMessage(message.name)
        writer.write(message.name)
        return this
    }

    fun sendMessage(error: Error): Client {
        logMessage(error.name)
        writer.write(error.message())
        return this
    }

    fun closeSilently() {
        try {
            socket.close()
        } catch (ignored: Exception) {
        }
    }

    private fun logMessage(message: String) {
        var info = "Sending $message"
        if (name != null) {
            info += " to $name"
        }
        Log.print(info)
    }

    override fun toString(): String {
        return "Client(currentColor=$currentColor, socket=$socket)"
    }

}