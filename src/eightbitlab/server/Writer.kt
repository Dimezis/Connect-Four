package eightbitlab.server

import java.io.PrintWriter
import java.net.Socket

class Writer(socket: Socket) {
    private val writer = PrintWriter(socket.outputStream, true)

    fun write(message: String) {
        writer.println(message)
    }
}