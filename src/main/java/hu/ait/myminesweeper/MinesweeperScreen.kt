package hu.ait.myminesweeper

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MinesweeperGame() {
    val gridSize = 10
    val mineCount = 15
    var board by remember { mutableStateOf(generateBoardWithMines(gridSize, mineCount)) }
    var isFirstClick by remember { mutableStateOf(true) }
    var emojiState by remember { mutableStateOf("😀") }
    var isFlagMode by remember { mutableStateOf(false) }
    var incorrectFlag by remember { mutableStateOf(false) }
    var hasWon by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = emojiState,
            fontSize = 48.sp
        )

        if (incorrectFlag) {
            Text(
                text = stringResource(R.string.not_a_mine),
                fontSize = 24.sp,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        if (hasWon) {
            Text(
                text = stringResource(R.string.you_won),
                fontSize = 32.sp,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.flag),
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            Checkbox(
                checked = isFlagMode,
                onCheckedChange = { isChecked ->
                    isFlagMode = isChecked
                }
            )
        }

        MinesweeperBoard(board) { row, col ->
            if (isFlagMode) {
                board = board.mapIndexed { rIndex, rList ->
                    rList.mapIndexed { cIndex, cell ->
                        if (rIndex == row && cIndex == col) {
                            val newFlaggedState = !cell.isFlagged

                            if (newFlaggedState && !cell.isMine) {
                                incorrectFlag = true
                                emojiState = "😢"
                            }

                            cell.copy(isFlagged = !cell.isFlagged)
                        } else {
                            cell
                        }
                    }
                }

                if (checkWinCondition(board, mineCount)) {
                    hasWon = true
                    emojiState = "😎"
                }

            } else {
                if (isFirstClick) {
                    if (board[row][col].isMine) {
                        emojiState = "😢"
                        board = revealAllMines(board)
                    }
                    board = revealFirstClick(board, row, col)
                    isFirstClick = false
                } else {
                    if (board[row][col].isMine) {
                        emojiState = "😢"
                        board = revealAllMines(board)
                    }
                    board = board.mapIndexed { rIndex, rList ->
                        rList.mapIndexed { cIndex, cell ->
                            if (rIndex == row && cIndex == col) {
                                cell.copy(isRevealed = true)
                            } else {
                                cell
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            board = generateBoardWithMines(gridSize, mineCount)
            isFirstClick = true
            emojiState = "😀"
            incorrectFlag = false
            hasWon = false
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            )
        ) {
            Text("Reset Game")
        }
    }
}

fun checkWinCondition(board: List<List<Cell>>, mineCount: Int): Boolean {
    var flaggedMines = 0
    var incorrectFlags = false

    for (row in board) {
        for (cell in row) {
            if (cell.isFlagged && cell.isMine) {
                flaggedMines++
            } else if (cell.isFlagged) {
                incorrectFlags = true
            }
        }
    }

    return flaggedMines == mineCount && !incorrectFlags
}

fun revealAllMines(board: List<List<Cell>>): List<List<Cell>> {
    return board.map { row ->
        row.map { cell ->
            if (cell.isMine) {
                cell.copy(isRevealed = true)
            } else {
                cell
            }
        }
    }
}

fun revealFirstClick(board: List<List<Cell>>, row: Int, col: Int): List<List<Cell>> {
    val newBoard = board.map { it.toMutableList() }.toMutableList()

    revealCells(newBoard, row, col)

    return newBoard
}

fun revealCells(board: MutableList<MutableList<Cell>>, row: Int, col: Int) {
    if (row !in board.indices || col !in board[row].indices || board[row][col].isRevealed) return

    board[row][col] = board[row][col].copy(isRevealed = true)

    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1,        0 to 1,
        1 to -1,  1 to 0,  1 to 1
    )

    for ((dr, dc) in directions) {
        val newRow = row + dr
        val newCol = col + dc

        if (newRow in board.indices && newCol in board[newRow].indices && !board[newRow][newCol].isMine) {
            board[newRow][newCol] = board[newRow][newCol].copy(isRevealed = true)
        }
    }
}

fun countNeighboringMines(board: List<List<Cell>>, row: Int, col: Int): Int {
    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1,         0 to 1,
        1 to -1,  1 to 0,  1 to 1
    )
    return directions.count { (dr, dc) ->
        val newRow = row + dr
        val newCol = col + dc
        newRow in board.indices && newCol in board[newRow].indices && board[newRow][newCol].isMine
    }
}
