package eightbitlab.server.protocol

import eightbitlab.server.Channel
import eightbitlab.server.Client
import eightbitlab.server.Log
import eightbitlab.server.Server

enum class Command {
    JOIN {
        override fun execute(argument: String, server: Server, client: Client) {
            if (client.name == null) {
                client.sendMessage(Error.NO_NAME_SET)
                return
            }
            tryToJoinChannel(argument, client, server)
        }

        private fun tryToJoinChannel(name: String, client: Client, server: Server) {
            var channel = server.channelsMap[name]
            if (channel != null) {
                if (channel.hasPlaces()) {
                    channel.join(client)
                } else {
                    client.sendMessage(Error.CHANNEL_IS_FULL)
                }
            } else {
                channel = Channel(name)
                channel.join(client)
                server.channelsMap.put(name, channel)
            }

            Log.print("$client joining to channel $channel")
        }
    },

    PUT {
        override fun execute(argument: String, server: Server, client: Client) {
            val channel = client.currentChannel

            if (channel == null) {
                client.sendMessage(Error.NOT_JOINED_TO_CHANNEL)
                return
            }
            try {
                val columnIndex = argument.toInt()
                channel.put(client, columnIndex)
            } catch (e: NumberFormatException) {
                client.sendMessage(Error.WRONG_COLUMN_FORMAT.message() + " $argument")
                onError(client, e)
            } catch (e: IllegalArgumentException) {
                client.sendMessage(e.message!!)
                onError(client, e)
            } catch (e: IllegalStateException) {
                client.sendMessage(e.message!!)
                onError(client, e)
            }
        }

        private fun onError(client: Client, e: Exception) {
            Log.error(e.message!!)
            sendWinMessageToOpponent(client)
            kickOut(client)
            client.currentChannel?.clearChannel()
        }

        private fun sendWinMessageToOpponent(client: Client) {
            with(client) {
                currentChannel
                        ?.getOpponentOf(this)
                        ?.sendMessage(Message.YOU_WON)
                        ?.closeSilently()
            }
        }

        private fun kickOut(client: Client) {
            with(client) {
                sendMessage(Message.YOU_LOST)
                closeSilently()
            }
        }
    },

    NAME {
        override fun execute(argument: String, server: Server, client: Client) {
            client.name = argument
            client.sendMessage(Message.SUCCESS)
        }
    },

    EMPTY {
        override fun execute(argument: String, server: Server, client: Client) {
        }
    };

    abstract fun execute(argument: String, server: Server, client: Client)
}