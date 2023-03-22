package com.verbleGame.verble

import LsGlobal
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class VerbListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verblist)

        val lsVerbs = LsGlobal.lsVerbs
        val rvVerbHint = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvVerbHint)

        rvVerbHint.layoutManager = LinearLayoutManager(this)

//declare on click action and pass though adapter from here
        val verbsAdapter = VerbsAdapter(lsVerbs)
        rvVerbHint.adapter = verbsAdapter
    }


}
