package com.verbleGame.verble

import LsGlobal
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val txtName = findViewById<EditText>(R.id.txtName)
        val btnGotoList = findViewById<Button>(R.id.btnGotoList)

        try {
            val inputStream = assets.open("Verble_DefaultWords_List.txt")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            bufferedReader.useLines { lines ->
                lines.forEach { line ->
                    val parts = (line.split(" - "))
                    val verbModel = VerbModel(parts[0].uppercase(), parts[1])
                    LsGlobal.lsVerbs.add(verbModel)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        btnGotoList.setOnClickListener{
            val intent = Intent(this@MainActivity, VerbListActivity::class.java)
            startActivity(intent)
        }

        btnPlay.setOnClickListener{
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            if(txtName.text.isNotEmpty()) {
                Toast.makeText(this, "Hello " + txtName.text.trim(), Toast.LENGTH_LONG).show()
                val pName = txtName.text.toString()
                intent.putExtra("name", pName)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_LONG).show()
            }
        }

        btnExit.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit Verble?")
                .setPositiveButton("Yes") { _, _ ->
                    finishAffinity() //closes all running activities
                }
                .setNegativeButton("No", null)
                .create()
                .show()
        }
    }
}
