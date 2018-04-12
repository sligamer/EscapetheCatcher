package com.lab75.sligamer.escapethecatcher

/**
 * Created by Justin Freres on 4/10/2018.
 * Lab 7-5 Escape the Catcher
 * Plugin Support with kotlin_version = '1.2.31'
 */

class Enemy {
    private var mRow: Int = 0
    private var mCol: Int = 0

    fun move(gameBoard: BoardInfo, preyCol: Int, preyRow: Int) {

        when {
            mCol < preyCol && gameBoard.Board[mRow][mCol + 1] == BoardCodes.isEMPTY ->  {
                mCol++
            }
            mCol > preyCol && gameBoard.Board[mRow][mCol - 1] == BoardCodes.isEMPTY ->  {
                mCol--
            }
            mRow < preyRow && gameBoard.Board[mRow + 1][mCol] == BoardCodes.isEMPTY ->  {
                mRow++
            }
            mRow > preyRow && gameBoard.Board[mRow - 1][mCol] == BoardCodes.isEMPTY ->  {
                mRow--
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