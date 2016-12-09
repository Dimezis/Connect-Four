package eightbitlab.server

import eightbitlab.server.protocol.Command

//TODO unit test
class CommandParser {

    @Throws(IllegalArgumentException::class)
    fun parse(value: String): Pair<Command, String> {
        val args = value.split(" ")
        val command = Command.valueOf(args[0])
        if (args.size > 1) {
            return oneArgumentCommand(command, args)
        }
        return noArgumentCommand(command)
    }

    private fun oneArgumentCommand(command: Command, args: List<String>): Pair<Command, String> {
        val argument = args[1]
        return Pair(command, argument)
    }

    fun empty(): Pair<Command, String> = noArgumentCommand(Command.EMPTY)


    private fun noArgumentCommand(command: Command) = Pair(command, "")

}