package com.tictactoe

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TicTacToe : AppCompatActivity(), View.OnClickListener {

    private var buttons: Array<Array<Button>> = emptyArray()
    private var player1Turn = true
    private var roundCount = 0
    private var player1Points = 0
    private var player2Points = 0
    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tictactoe)
        textViewPlayer1 = findViewById(R.id.text_view_p1)
        textViewPlayer2 = findViewById(R.id.text_view_p2)
        textViewPlayer1.setText(intent.extras?.getString("editTextPlayer1") + ": 0")
        textViewPlayer2.setText(intent.extras?.getString("editTextPlayer2") + ": 0")
        Toast.makeText(this, "Hallo players", Toast.LENGTH_SHORT).show()
        initializeButtonArray()

        val buttonReset: Button = findViewById(R.id.button_reset)
        buttonReset.setOnClickListener {
            resetGame()
        }
    }

    private fun initializeButtonArray(){
        val resID00 = resources.getIdentifier("button_00", "id", packageName)
        val resID01 = resources.getIdentifier("button_01", "id", packageName)
        val resID02 = resources.getIdentifier("button_02", "id", packageName)
        val resID10 = resources.getIdentifier("button_10", "id", packageName)
        val resID11 = resources.getIdentifier("button_11", "id", packageName)
        val resID12 = resources.getIdentifier("button_12", "id", packageName)
        val resID20 = resources.getIdentifier("button_20", "id", packageName)
        val resID21 = resources.getIdentifier("button_21", "id", packageName)
        val resID22 = resources.getIdentifier("button_22", "id", packageName)
        buttons = arrayOf(
                arrayOf(
                    findViewById(resID00),
                    findViewById(resID01),
                    findViewById(resID02)
                ), arrayOf(
                    findViewById(resID10),
                    findViewById(resID11),
                    findViewById(resID12)
                ), arrayOf(
                    findViewById(resID20),
                    findViewById(resID21),
                    findViewById(resID22)
            ))
        for (i in 0 until 3){
            for(j in 0 until 3){
                buttons[i][j].setOnClickListener(this)
            }
        }
    }

    override fun onClick(v: View?) {
        var button: Button = v as Button
        if(!button.text.toString().equals("")){
            return
        }
        if(player1Turn){
            button.text = intent.extras?.getString("editTextSymPlayer1")
        } else {
            button.text = intent.extras?.getString("editTextSymPlayer2")
        }
        roundCount ++
        if(checkForWin()){
            if(player1Turn){
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            player1Turn = !player1Turn
        }
    }

    private fun checkForWin(): Boolean {
        val field: Array<Array<String>> = arrayOf(arrayOf<String>("", "", ""), arrayOf<String>("", "", ""), arrayOf<String>("", "", ""))
        for (i in 0 until 3){
            for(j in 0 until 3){
                field[i][j] = buttons.get(i).get(j).text.toString()
            }
        }
        for (i in 0 until 3){
            if(field[i][0].equals(field[i][1])
                    and field[i][0].equals(field[i][2])
                    and !field[i][0].equals("")){
                return true
            }
        }
        for (i in 0 until 3){
            if(field[0][i].equals(field[1][i])
                    and field[0][i].equals(field[2][i])
                    and !field[0][i].equals("")){
                return true
            }
        }
        for (i in 0 until 3){
            if(field[0][0].equals(field[1][1])
                    and field[0][0].equals(field[2][2])
                    and !field[0][0].equals("")){
                return true
            }
        }
        for (i in 0 until 3){
            if(field[0][2].equals(field[1][1])
                    and field[0][2].equals(field[2][0])
                    and !field[0][2].equals("")){
                return true
            }
        }
        return false
    }

    private fun player1Wins(){
        player1Points++
        Toast.makeText(this, intent.extras?.getString("editTextPlayer1") + " wins", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins(){
        player2Points++
        Toast.makeText(this, intent.extras?.getString("editTextPlayer2") + " wins", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun updatePointsText(){
        val player1placeholder = intent.extras?.getString("editTextPlayer1")
        val player2placeholder = intent.extras?.getString("editTextPlayer2")
        textViewPlayer1.setText( "$player1placeholder: " + player1Points)
        textViewPlayer2.setText("$player2placeholder: " + player2Points)
    }

    private fun resetBoard(){
        for (i in 0 until 3){
            for(j in 0 until 3){
                buttons[i][j].setText("")
            }
        }
        roundCount = 0
        player1Turn = true
    }

    private fun resetGame(){
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
    }

    // Saves state of game if rotation heppens
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("roundCount", roundCount)
        outState.putInt("player1Points", player1Points)
        outState.putInt("player2Points", player2Points)
        outState.putBoolean("player1Turn", player1Turn)
    }

    // Transfers state of game to rotated phone
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        roundCount = savedInstanceState.getInt("roundCount")
        player1Points = savedInstanceState.getInt("player1Points")
        player2Points = savedInstanceState.getInt("player2Points")
        player1Turn = savedInstanceState.getBoolean("player1Turn")
    }
}