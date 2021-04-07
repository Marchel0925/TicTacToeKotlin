package com.tictactoe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("StaticFieldLeak")
private lateinit var editTextPlayer1 : EditText
@SuppressLint("StaticFieldLeak")
private lateinit var editTextPlayer2 : EditText
@SuppressLint("StaticFieldLeak")
private lateinit var editTextSymPlayer1 : EditText
@SuppressLint("StaticFieldLeak")
private lateinit var editTextSymPlayer2 : EditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPlayer1 = findViewById(R.id.edit_text_Player1)
        editTextPlayer2 = findViewById(R.id.edit_text_Player2)
        editTextSymPlayer1 = findViewById(R.id.edit_text_sym_Player1)
        editTextSymPlayer2 = findViewById(R.id.edit_text_sym_Player2)

        val buttonOK: Button = findViewById(R.id.button_OK)
        buttonOK.setOnClickListener {
            val valueBundle: Bundle = Bundle()
            if(editTextPlayer1.text.toString().equals("")){
                valueBundle.putString("editTextPlayer1", "Player 1")
            }else{
                valueBundle.putString("editTextPlayer1", editTextPlayer1.text.toString())
            }

            if(editTextPlayer2.text.toString().equals("")){
                valueBundle.putString("editTextPlayer2", "Player 2")
            }else{
                valueBundle.putString("editTextPlayer2", editTextPlayer2.text.toString())
            }

            if(editTextSymPlayer1.text.toString().equals("")){
                valueBundle.putString("editTextSymPlayer1", "X")
            }else{
                valueBundle.putString("editTextSymPlayer1", editTextSymPlayer1.text.toString())
            }

            if(editTextSymPlayer2.text.toString().equals("")){
                valueBundle.putString("editTextSymPlayer2", "O")
            }else{
                valueBundle.putString("editTextSymPlayer2", editTextSymPlayer2.text.toString())
            }
            val intent : Intent = Intent(this@MainActivity, TicTacToe::class.java)
                    .putExtras(valueBundle)
            startActivity(intent)
        }
    }

    // Saves state if rotation heppens
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("editTextPlayer1", editTextPlayer1.text.toString())
        outState.putString("editTextPlayer2", editTextPlayer2.text.toString())
        outState.putString("editTextSymPlayer1", editTextSymPlayer1.text.toString())
        outState.putString("editTextSymPlayer2", editTextSymPlayer2.text.toString())
    }

    // Transfers state to rotated phone
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editTextPlayer1.setText(savedInstanceState.getString("editTextPlayer1"))
        editTextPlayer2.setText(savedInstanceState.getCharSequence("editTextPlayer2"))
        editTextSymPlayer1.setText(savedInstanceState.getCharSequence("editTextSymPlayer1"))
        editTextSymPlayer2.setText(savedInstanceState.getCharSequence("editTextSymPlayer2"))
    }
}