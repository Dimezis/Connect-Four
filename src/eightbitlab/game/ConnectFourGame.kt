package eightbitlab.game

import eightbitlab.server.Log
import eightbitlab.server.protocol.Error

class ConnectFourGame {

//     0 1 2 3 4 5 6

//    | | | | | | | |  0
//    | | | | | | | |  1
//    | | | | | | | |  2
//    | | | |Y|Y| | |  3
//    | | |R|Y|Y| | |  4
//    |R|R|Y|Y|R|R|R|  5

    companion object {
        val RED = 'R'
        val YELLOW = 'Y'
        val EMPTY = ' '

        private val ROWS = 6
        private val COLUMNS = 7
    }

    //TODO use this enum for colors
    enum class Color(val value: Char) {
        RED('R'), YELLOW('Y')
    }

    private val winChecker = WinChecker()
    private val board = matrix2d(ROWS, COLUMNS, { row: Int, width: Int -> CharArray(width) { EMPTY } })

    private var currentMoveColor = RED
    var moveCount = 0
    /**
     * @return char representing a winner color.
     * Can be null if it's a draw
     */
    var onGameEnd: (winner: Char?) -> Unit = { }
    var onMove: (currentColor: Char) -> Unit = { }

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    fun put(color: Char, column: Int) {
        if (column < 0 || column >= COLUMNS) {
            throw IllegalArgumentException(Error.INVALID_COLUMN.message())
        }
        if (columnIsFull(column)) {
            throw IllegalArgumentException(Error.COLUMN_IS_FULL.message())
        }
        if (currentMoveColor != color) {
            throw IllegalStateException(Error.NOT_YOUR_TURN.message())
        }

        putToHighestPosition(color, column)
        updateCurrentMoveColor()
        checkGameEnd(color)
        moveCount++
    }

    private fun boardIsFull(): Boolean {
        board[0].forEach {
            if (it == EMPTY) {
                return false
            }
        }
        return true
    }

    private fun updateCurrentMoveColor() {
        if (currentMoveColor == RED) {
            currentMoveColor = YELLOW
        } else {
            currentMoveColor = RED
        }
    }

    private fun checkGameEnd(currentColor: Char) {
        if (winChecker.checkWin(currentColor, board)) {
            onGameEnd(currentColor)
        } else if (boardIsFull()) {
            onGameEnd(null)
        } else {
            onMove(currentColor)
        }
    }

    /**
     * @return index of the row
     */
    private fun putToHighestPosition(color: Char, column: Int): Int {
        for (row in (ROWS - 1) downTo 0) {
            if (board[row][column] == EMPTY) {
                board[row][column] = color
                return row
            }
        }
        Log.print("No row to put $color in column $column")
        throw RuntimeException(Error.COLUMN_IS_FULL.message())
    }

    private fun columnIsFull(column: Int) = board[0][column] != EMPTY

    fun getBoardAsString(): String {
        val stringBuilder = StringBuilder()
        for (row in board) {
            for (cell in row) {
                stringBuilder.append('|')
                stringBuilder.append(cell)
            }
            stringBuilder.append("|\n")
        }
        return stringBuilder.toString()
    }

    inline fun matrix2d(
            height: Int, width: Int,
            init: (Int, Int) -> CharArray) = Array(height, { row -> init(row, width) }
    )
}