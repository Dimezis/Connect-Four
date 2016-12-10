object Resource {
    internal var boardNoWinRLast = arrayOf(
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('Y', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('R', 'R', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('R', 'Y', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('R', 'Y', ' ', ' ', ' ', ' ', ' '))

    internal var boardWinRUp = arrayOf(
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', 'R'),
            charArrayOf(' ', ' ', ' ', ' ', ' ', 'Y', 'R'),
            charArrayOf(' ', ' ', ' ', ' ', ' ', 'Y', 'R'),
            charArrayOf(' ', ' ', ' ', ' ', ' ', 'Y', 'R'))

    internal var boardWinYUp = arrayOf(
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', 'Y', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', 'Y', 'R'),
            charArrayOf(' ', ' ', ' ', ' ', ' ', 'Y', 'R'),
            charArrayOf(' ', ' ', ' ', ' ', 'R', 'Y', 'R'))

    internal var boardWinRDiagonal = arrayOf(
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', 'R', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', 'R', 'R', ' ', ' '),
            charArrayOf(' ', ' ', ' ', 'Y', 'Y', 'R', ' '),
            charArrayOf(' ', ' ', ' ', 'R', 'Y', 'Y', 'R'),
            charArrayOf(' ', ' ', 'Y', 'Y', 'R', 'Y', 'R'))

    internal var boardWinYUp2 = arrayOf(
            charArrayOf(' ', 'Y', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('R', 'Y', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('R', 'Y', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('R', 'Y', 'R', 'Y', 'R', 'Y', 'R'),
            charArrayOf('Y', 'R', 'Y', 'R', 'Y', 'R', 'Y'),
            charArrayOf('R', 'Y', 'R', 'Y', 'R', 'Y', 'R'))

    internal var boardWinRHorizontal = arrayOf(
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('Y', ' ', ' ', ' ', ' ', ' ', ' '),
            charArrayOf('Y', 'Y', ' ', 'R', 'R', 'R', 'R'))

    internal var boardWinRHorizontalTop = arrayOf(
            charArrayOf('Y', 'R', 'R', 'R', 'R', ' ', ' '),
            charArrayOf('R', 'Y', 'R', 'Y', 'R', ' ', ' '),
            charArrayOf('R', 'Y', 'Y', 'Y', 'Y', 'Y', ' '),
            charArrayOf('R', 'Y', 'R', 'Y', 'R', 'Y', 'R'),
            charArrayOf('Y', 'R', 'Y', 'R', 'Y', 'R', 'Y'),
            charArrayOf('R', 'Y', 'R', 'Y', 'R', 'Y', 'R'))
}
