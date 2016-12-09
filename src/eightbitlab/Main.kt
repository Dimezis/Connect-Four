package eightbitlab

import eightbitlab.server.Server

fun main(args: Array<String>) {
    var port = 4242
    var address = "0.0.0.0"

    if (args.isNotEmpty()) {
        address = args[0]
        port = args[1].toInt()
    }

    val server = Server(address, port)

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            server.close()
        }
    })
}
