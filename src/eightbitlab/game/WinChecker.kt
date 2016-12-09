package eightbitlab.game

import eightbitlab.game.ConnectFourGame.Companion.EMPTY

class WinChecker {

    fun checkWin(currentChar: Char, board: Array<CharArray>): Boolean {
        val height = board.size
        val width = board[0].size

        for (r in 0..height - 1) {
            for (c in 0..width - 1) {
                val cell = board[r][c]
                if (cell == EMPTY || cell != currentChar) {
                    continue
                }

                // look right
                if (c + 3 < width && cell == board[r][c + 1] && cell == board[r][c + 2] && cell == board[r][c + 3]) {
                    return true
                }

                if (r + 3 < height) {
                    if (cell == board[r + 1][c] && cell == board[r + 2][c] && cell == board[r + 3][c]) { // look down
                        return true
                    } else if (c + 3 < width && cell == board[r + 1][c + 1] && cell == board[r + 2][c + 2] && cell == board[r + 3][c + 3]) { // look down & right
                        return true
                    } else if (c - 3 >= 0 && cell == board[r + 1][c - 1] && cell == board[r + 2][c - 2] && cell == board[r + 3][c - 3]) { // look down & left
                        return true
                    }
                }
            }
        }
        return false
    }

}