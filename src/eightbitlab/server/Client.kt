package eightbitlab.server

import eightbitlab.server.protocol.Error
import eightbitlab.server.protocol.Message
import java.net.Socket

class Client(val socket: Socket, private val writer: Writer) {

    var currentChannel: Channel? = null
    var currentColor: Char? = null
    var name: String? = null

    fun sendMessage(message: String): Client {
        writer.write(message)
        return this
    }

    fun sendMessage(message: Message): Client {
        Log.print("Sending $message")
        writer.write(message.name)
        return this
    }

    fun sendMessage(error: Error): Client {
        writer.write(error.message())
        return this
    }

    fun closeSilently() {
        try {
            socket.close()
        } catch (ignored: Exception) {
        }
    }

    fun leaveChannel() {
        currentChannel?.remove(this)
        currentColor = null
    }

    override fun toString(): String {
        return "Client(currentColor=$currentColor, socket=$socket)"
    }

}