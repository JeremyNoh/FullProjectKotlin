package com.example.testsomethings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun trysom(view: View){
        val nom = findViewById<EditText>(R.id.nom)
        val prenom = findViewById<EditText>(R.id.prenom)
        val info = findViewById<TextView>(R.id.info)
        info.text = " bonjour et bienvenu sur cette app ${prenom.text} ${nom.text}"
        Toast.makeText(this,"au moins sa qui marche",Toast.LENGTH_LONG).show()
        val inte = Intent(this, SecondScreen::class.java)
        startActivity( Intent(inte))
    }


}
