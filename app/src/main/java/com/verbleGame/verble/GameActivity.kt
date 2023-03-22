package com.verbleGame.verble

import LsGlobal
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import charListToSpacedString
import validateGuess
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //components
        val btnGuess = findViewById<Button>(R.id.btnGuess)
        val btnNewGame = findViewById<Button>(R.id.btnNewGame)
        val tvLD = findViewById<TextView>(R.id.tvLetterDisplay)
        val tvInc = findViewById<TextView>(R.id.tvIncorrect)
        val tvDone = findViewById<TextView>(R.id.tvGameOver)
        val tvAnswer = findViewById<TextView>(R.id.tvAnswer)
        val tvHint = findViewById<TextView>(R.id.tvHintDisplay)
        val tvPlayer = findViewById<TextView>(R.id.tvPlayerName)

        //var pName = intent.getStringExtra("name") ?: "Player"
        //tvPlayer.text = pName

        //lists
        val lsVerbs = LsGlobal.lsVerbs
        val lsCorrect =
            mutableListOf<Char>() //holds a list of characters that are displayed in tvLetterDisplay. '_' - letters that haven't been guessed, and 'A'..'Z' for the corresponding guessed letter.
        val lsIncorrect =
            mutableListOf<Char>() //holds a list of incorrectly guessed characters that are displayed in tvIncorrect
        var ansList =
            mutableListOf<Char>() //holds a list of correct letters in the correct order.

        //single variables and values
        var gameOver: Boolean //false-game is running, true-game is over
        var answer: String
        var hint: String

        //button (btnGuess) when clicked, pops up an alertdialog that receives a user input of the letter that they want to guess.
        btnGuess.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Guess a letter")

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK") { _, _ ->
                val inputFormatted = input.text.toString().uppercase().trim() //gets the input as an uppercase string
                if (!validateGuess(inputFormatted)) { //guess is validated using !validateGuess function in HelperClasses.kt
                    Toast.makeText(this, "Single Letter Required", Toast.LENGTH_LONG)
                        .show() //toast shown if the entered string does match the regex
                } else {
                    val guess = inputFormatted.single() //guessed letter stored in variable
                    if (lsCorrect.contains(guess) or lsIncorrect.contains(guess)) { //checks if the guessed letter has already been guessed
                        Toast.makeText(this, "\'$guess\' already guessed", Toast.LENGTH_SHORT).show()
                    }else{
                    //check if guess is correct else (guess incorrect)
                    if (ansList.contains(guess)) { //correct guess
                        val lsIndices =
                            ansList.indices.filter { ansList[it] == guess } //get the indices where the correctly guessed letter is present within the answer list.
                        for (i in lsIndices.indices) { //for each item in lsIndices (lsIndices contains corresponding indices to the guessed letter)
                            lsCorrect[lsIndices[i]] = guess //set the values of lsCorrect at the corresponding indices (lsIndices[i]) to the guessed letter (guess)
                            tvLD.text = charListToSpacedString(lsCorrect) //outputs guessed letters and underscores to tvLetterDisplay (tvLD)
                            if (!lsCorrect.contains('_')) { //if the correct letter list does no contain any more underscores, all letters have been guessed and the game is over.
                                gameOver = true
                                btnGuess.visibility = View.GONE
                                tvDone.visibility = View.VISIBLE
                                tvInc.text = ""
                                Toast.makeText(this, "Well Done!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else { //incorrect guess
                        lsIncorrect.add(guess)
                        tvInc.text = charListToSpacedString(lsIncorrect)
                        Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
                    }
                }
                }
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }

        btnNewGame.setOnClickListener {
            gameOver = false
            btnGuess.visibility = View.VISIBLE
            tvDone.visibility = View.GONE
            lsIncorrect.clear()
            lsCorrect.clear()
            tvInc.text = ""

            //val max = lsVerbs.max()
            val randomInt = Random.nextInt(999)
            answer = lsVerbs[randomInt].verb
            hint = lsVerbs[randomInt].hint
            tvHint.text = "$hint:"

            val len = answer.length
            ansList = answer.toList() as MutableList<Char>

            for (i in 1..len) {
                lsCorrect.add('_')
                tvLD.text = charListToSpacedString(lsCorrect)
            }

            tvLD.text = charListToSpacedString(lsCorrect)
            tvAnswer.text = answer
        }
    }
}


