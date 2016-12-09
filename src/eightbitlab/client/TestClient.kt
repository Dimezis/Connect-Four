package eightbitlab.client

import eightbitlab.server.Writer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class TestClient(localSocketAddress: SocketAddress?) {

    init {
        val client = Socket()
        client.connect(localSocketAddress)
        val writer = Writer(client)

        Thread({
            val reader = BufferedReader(InputStreamReader(client.inputStream))
            reader.lines()
                    .forEach {
                        println("Client reads " + it)
                    }
        }).start()

        while (true) {
            writer.write(readLine()!!)
        }
    }

}

fun main(vararg args: String): Unit {
    TestClient(InetSocketAddress(InetAddress.getByName("0.0.0.0"), 4242))
}

