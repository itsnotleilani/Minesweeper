# Minesweeper

A Kotlin-based Android game inspired by the classic Minesweeper. Built using Jetpack Compose, the app incorporates custom logic for board generation, mine placement, cell revealing, and win/loss conditions. The game features interactive UI elements like flagging cells, emoji-based game feedback, and visual cues for revealed mines. Game state is handled using functional programming and state-aware UI components.

## Features

* Dynamic Board Generation: Each new game board is randomized with a set number of hidden mines and calculated neighboring mine counts.
* Cell Revealing Logic: Recursive cell reveal mimics traditional Minesweeper behavior with support for auto-revealing empty cells.
* Flagging System: Toggle flag mode to mark suspected mines. Incorrect flags are indicated with visual and emoji feedback.
* Win/Loss Detection: The game tracks win conditions based on correct flagging and reveals all mines when a mine is clicked.
* Responsive UI: Built entirely with Jetpack Compose, including animations, emojis, and conditionally rendered messages.
* Reset Button: Restart the game at any time with a clean new board.

## Technology Stack

* Kotlin: Main programming language with emphasis on data classes and functional state updates.
* Jetpack Compose: For creating reactive, declarative UI elements and managing layout responsiveness.
* OOP & Data Structures:
  * Cell is a well-structured data class modeling each tile.
  * List<List<Cell>> is used to manage the 2D board.
* Recursive and iterative algorithms handle mine placement, neighbor counting, and reveal behavior.
* State Management: Leveraging remember and mutableStateOf to persist and update game state in a composable way.

## How to Play
* Tap a cell to reveal it.
* Toggle flag mode to mark cells you believe contain mines.
* Avoid clicking mines — one wrong move ends the game.
* Win by flagging all mines without placing any incorrect flags.
* Hit "Reset Game" to play again with a new randomized board.

## Installation
Clone the repository
``bash
git clone https://github.com/itsnotleilani/Minesweeper.git
``

Open the project in Android Studio.

Ensure Kotlin and Jetpack Compose dependencies are installed.

Run the app on a local emulator or physical Android device.

<img width="181" alt="Screenshot 2025-07-06 at 11 03 03 PM" src="https://github.com/user-attachments/assets/660ab21a-79a1-4f0e-a918-910e60cb15af" />
<img width="179" alt="Screenshot 2025-07-06 at 11 07 23 PM" src="https://github.com/user-attachments/assets/80f970e8-282c-43ea-a8ce-c0665bb296fc" />
<img width="182" alt="Screenshot 2025-07-06 at 11 03 43 PM" src="https://github.com/user-attachments/assets/180ca629-cfe0-4ec4-b687-de8dbe78cadc" />
