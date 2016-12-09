package eightbitlab.server.protocol

enum class Error {
    INVALID_COMMAND,
    CHANNEL_IS_FULL,
    INVALID_COLUMN,
    COLUMN_IS_FULL,
    NOT_JOINED_TO_CHANNEL,
    WRONG_COLUMN_FORMAT,
    NOT_YOUR_TURN,
    NO_NAME_SET,
    ALREADY_IN_CHANNEL;

    fun message(): String {
        return "Error $name"
    }
}