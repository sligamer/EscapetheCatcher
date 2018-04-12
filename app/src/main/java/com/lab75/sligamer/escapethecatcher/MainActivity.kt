package com.lab75.sligamer.escapethecatcher

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast



/**
 * Created by Justin Freres on 4/10/2018.
 * Lab 7-5 Escape the Catcher
 * Plugin Support with kotlin_version = '1.2.31'
 */

class MainActivity : AppCompatActivity(), GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    // DECLARE VARIABLES
    private lateinit var aGestureDetector: GestureDetector

    // BOARD INFO
    companion object {
        var SQUARE: Int = 130
        var OFFSET: Int = 70
        var COLUMNS: Int = 8
        var ROWS: Int = 8

    }
    private var gameBoard: BoardInfo = BoardInfo()

    // VISUAL OBJECTS ARE ORGANIZED IN AN ARRAYLIST
    private lateinit var visualObjects: ArrayList<ImageView>
    private lateinit var player: Player
    private lateinit var enemy: Enemy

    // LAYOUT AND INTERACTIVE INFO
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var enemyImageView: ImageView
    private lateinit var playerImageView: ImageView
    private lateinit var obstacleImageView: ImageView
    private lateinit var exitImageView: ImageView

    private var exitRow: Int = 0
    private var exitCol: Int = 0

    // WINS AND LOSSES
    private var wins: Int = 0
    private var losses: Int = 0
    private lateinit var winsTextView: TextView
    private lateinit var lossesTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TASK 1: SET THE LAYOUT CONTENT FOR THE ACTIVITY
        setContentView(R.layout.activity_main)

        // TASK 2: REF THE ACTIVITY AND TEXT VIEWS
        constraintLayout = findViewById(R.id.constraintLayout)
        winsTextView = findViewById(R.id.winstextView)
        lossesTextView = findViewById(R.id.lossestextView)

        // TASK 3: INSTANTIATE THE LAYOUT INFLATER
        layoutInflater.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)

        // TASK 4: BUILD THE LOGIC BOARD AND CONSTRUCT THE GAME
        visualObjects = ArrayList<ImageView>()
        buildLogicBoard()
        createEnemy()
        createPlayer()
        wins = 0
        losses = 0
        winsTextView.text = ("Wins: $wins").toString()
        lossesTextView.text = ("Losses: $losses").toString()

        // TASK 5: INSTANTIATE A GESTURE DETECTOR
        aGestureDetector = GestureDetector(this, this)
        aGestureDetector.setOnDoubleTapListener(this)

    }

    private fun createPlayer() {
        var row = 1
        var col = 1

        playerImageView = layoutInflater.inflate(R.layout.player_layout, null) as ImageView
        playerImageView.x = (col * SQUARE + OFFSET).toFloat()
        playerImageView.y = (row * SQUARE + OFFSET).toFloat()
        constraintLayout.addView(playerImageView, 0)

        player = Player()
        player.setRow(row)
        player.setCol(col)
        visualObjects.add(playerImageView)
    }

    private fun createEnemy() {
        var row = 2
        var col = 4

        enemyImageView = layoutInflater.inflate(R.layout.enemy_layout, null) as ImageView
        enemyImageView.x = (col * SQUARE + OFFSET).toFloat()
        enemyImageView.y = (row * SQUARE + OFFSET).toFloat()
        constraintLayout.addView(enemyImageView, 0)

        enemy = Enemy()
        enemy.setRow(row)
        enemy.setCol(col)
        visualObjects.add(enemyImageView)

    }

    private fun buildLogicBoard() {

        for (row in 0 until ROWS) {
            for (col in 0 until COLUMNS) {
                if(gameBoard.Board[row][col] == BoardCodes.isOBSTACLE)
                {
                    obstacleImageView = layoutInflater.inflate(R.layout.obstacle_layout, null)
                            as ImageView
                    obstacleImageView.x = (col * SQUARE + OFFSET).toFloat()
                    obstacleImageView.y = (row * SQUARE + OFFSET).toFloat()
                    constraintLayout.addView(obstacleImageView, 0)
                    visualObjects.add(obstacleImageView)

                }
                else if(gameBoard.Board[row][col] == BoardCodes.isHOME)
                {
                    exitImageView = layoutInflater.inflate(R.layout.exit_layout, null)
                            as ImageView
                    exitImageView.x = (col * SQUARE + OFFSET).toFloat()
                    exitImageView.y = (row * SQUARE + OFFSET).toFloat()
                    constraintLayout.addView(exitImageView, 0)
                    visualObjects.add(exitImageView)
                    exitRow = 5
                    exitCol = 7

                }
            }
        }
    }


    /**
     * Notified when a double-tap occurs.
     *
     * @param e The down motion event of the first tap of the double-tap.
     * @return true if the event is consumed, else false
     */
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return true
    }

    /**
     * Notified when an event within a double-tap gesture occurs, including
     * the down, move, and up events.
     *
     * @param e The motion event that occurred during the double-tap gesture.
     * @return true if the event is consumed, else false
     */
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }

    /**
     * Notified when a single-tap occurs.
     *
     *
     * Unlike [OnGestureListener.onSingleTapUp], this
     * will only be called after the detector is confident that the user's
     * first tap is not followed by a second tap leading to a double-tap
     * gesture.
     *
     * @param e The down motion event of the single-tap.
     * @return true if the event is consumed, else false
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }

    /**
     * The user has performed a down [MotionEvent] and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    override fun onShowPress(e: MotionEvent?) {

    }

    /**
     * Notified when a tap occurs with the up [MotionEvent]
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * Notified when a tap occurs with the down [MotionEvent]
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * Notified of a fling event when it occurs with the initial on down [MotionEvent]
     * and the matching up [MotionEvent]. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1 The first down motion event that started the fling.
     * @param e2 The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     * along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     * along the y axis.
     * @return true if the event is consumed, else false
     */
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        movePlayer(velocityX, velocityY)
        return true
    }

    private fun movePlayer(velocityX: Float, velocityY: Float) {
        var direction: String = "undetectable"

        // TASK 1: MOVE THE PLAYER IN THE FLING DIRECTION
        when {
            velocityX > 2500 -> direction = "RIGHT"
            velocityX < -2500 -> direction = "LEFT"
            velocityY > 2500 -> direction = "DOWN"
            velocityY < -2500 -> direction = "UP"
        }

        when {
            !direction.contains("undetectable") -> {
                player.move(gameBoard, direction)
                playerImageView.x = (player.getCol() * SQUARE + OFFSET).toFloat()
                playerImageView.y = (player.getRow() * SQUARE + OFFSET).toFloat()

                // TASK 2: IT IS NOW THE ENEMY'S TURN. MOVE THE ENEMY
                enemy.move(gameBoard, player.getCol(), player.getRow())
                enemyImageView.x = (enemy.getCol() * SQUARE + OFFSET).toFloat()
                enemyImageView.y = (enemy.getRow() * SQUARE + OFFSET).toFloat()

            }
        }

        // TASK 3: CHECK IF THE GAME IS OVER
        // CHECK IF THE ENEMY CATCHES PLAYER
        when {
            enemy.getCol() == player.getCol() && enemy.getRow() == player.getRow() -> {
                losses++
                lossesTextView.text = ("Losses: $losses").toString()
                startNewGame()
            }
            exitRow == player.getRow() && exitCol == player.getCol() -> {
                wins++
                winsTextView.text = ("Wins: $wins").toString()
                startNewGame()
            }
        }

    }

    private fun startNewGame() {

        // TASK 1: CLEAR THE BOARD AND REMOVE PLAYERS
        var howMany: Int = visualObjects.size

        for (i in 0 until howMany)
        {
            var visualObj = visualObjects.get(i)
            constraintLayout.removeView(visualObj)
        }

        visualObjects.clear()

        // TASK 2: REBUILD THE BOARD
        buildLogicBoard()

        // TASK 3: ADD PLAYERS
        createEnemy()
        createPlayer()
    }

    fun startNewGameMenu(item: MenuItem) {
        startNewGame()
        Toast.makeText(this, "New Game Started!", Toast.LENGTH_LONG).show()
    }

    /**
     * Notified when a scroll occurs with the initial on down [MotionEvent] and the
     * current move [MotionEvent]. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1 The first down motion event that started the scrolling.
     * @param e2 The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     * call to onScroll. This is NOT the distance between `e1`
     * and `e2`.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     * call to onScroll. This is NOT the distance between `e1`
     * and `e2`.
     * @return true if the event is consumed, else false
     */
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    /**
     * Notified when a long press occurs with the initial on down [MotionEvent]
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return aGestureDetector.onTouchEvent(event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId
        if(id == R.string.action_settings){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
