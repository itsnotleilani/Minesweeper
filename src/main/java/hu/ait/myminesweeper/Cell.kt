package hu.ait.myminesweeper

import kotlin.random.Random

data class Cell (
    val isMine: Boolean = false,
    val neighboringMines: Int = 0,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false
)

fun generateBoardWithMines(size: Int, mineCount: Int): List<List<Cell>> {
    val board = MutableList(size) { MutableList(size) { Cell() } }
    var minesPlaced = 0

    while (minesPlaced < mineCount) {
        val row = Random.nextInt(size)
        val col = Random.nextInt(size)
        if (!board[row][col].isMine) {
            board[row][col] = board[row][col].copy(isMine = true)
            minesPlaced++
        }
    }

    for (row in 0 until size) {
        for (col in 0 until size) {
            if (!board[row][col].isMine) {
                val neighboringMines = countNeighboringMines(board, row, col)
                board[row][col] = board[row][col].copy(neighboringMines = neighboringMines)
            }
        }
    }

    return board
}
