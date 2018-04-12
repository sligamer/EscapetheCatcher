package com.lab75.sligamer.escapethecatcher

/**
 * Created by Justin Freres on 4/10/2018.
 * Lab 7-5 Escape the Catcher
 * Plugin Support with kotlin_version = '1.2.31'
 */

class Player {
    private var mRow: Int = 0
    private var mCol: Int = 0

    fun move(gameBoard: BoardInfo, button: String) {

        when (button) {
            "RIGHT" ->
                when {
                    gameBoard.Board[mRow][mCol + 1] != BoardCodes.isOBSTACLE ->
                        mCol++
                }
            "LEFT" ->
                when {
                    gameBoard.Board[mRow][mCol - 1] != BoardCodes.isOBSTACLE ->
                        mCol--
                }
            "UP" ->
                when {
                    gameBoard.Board[mRow - 1][mCol] != BoardCodes.isOBSTACLE ->
                        mRow--
                }
            "DOWN" ->
                when {
                    gameBoard.Board[mRow + 1][mCol] != BoardCodes.isOBSTACLE ->
                        mRow++
                }
        }
    }

    fun setRow(row: Int)
    {
        mRow = row
    }

    fun getRow() : Int
    {
        return mRow
    }
    fun setCol(col: Int)
    {
        mCol = col
    }
    fun getCol(): Int
    {
        return mCol
    }
}