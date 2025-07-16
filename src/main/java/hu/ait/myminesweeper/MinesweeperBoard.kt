package hu.ait.myminesweeper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MinesweeperBoard(board: List<List<Cell>>, onCellClick: (Int, Int) -> Unit) {
         for (rowIndex in board.indices) {
                Row {
                    for (colIndex in board[rowIndex].indices) {
                        MinesweeperCell(
                            cell = board[rowIndex][colIndex],
                            onClick = {
                                onCellClick(rowIndex, colIndex)
                            }
                        )
                    }
                }
         }


}

@Composable
fun MinesweeperCell(cell: Cell, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cell_block),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        if (cell.isRevealed) {
            if (cell.isMine) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                )
            }
            else if (cell.neighboringMines == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(red = 135, green = 206, blue = 250))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue.copy(alpha = 0.5f))
                )
            }
        }

        Text(
            text = if (cell.isRevealed) {
                if (cell.isMine) {
                    "💣"
                } else if (cell.neighboringMines > 0) {
                    "${cell.neighboringMines}"
                } else {
                    ""
                }
            } else {
                ""
            },
            color = Color.Black
        )

        if (cell.isFlagged) {
            Text(text = "🚩", fontSize = 24.sp)
        }
    }
}
