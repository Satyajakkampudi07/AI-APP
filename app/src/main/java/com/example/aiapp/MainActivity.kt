package com.example.aiapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aiapp.databinding.ActivityMainBinding
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.signOutButton.setOnClickListener {
            if(auth.currentUser!=null)
            {
                auth.signOut()
                startActivity(Intent(this,signInActivity::class.java))
                finish()
            }
        }

        val eTPrompt= findViewById<EditText>(R.id.eTPrompt)
        val btnSubmit= findViewById<Button>(R.id.btnSubmit)
        val tVResult= findViewById<TextView>(R.id.tVResult)

        btnSubmit.setOnClickListener {
            val prompt= eTPrompt.text.toString()

            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = "" //Enter Your API Here
            )
            runBlocking {
                val response = generativeModel.generateContent(prompt)
                tVResult.text= response.text
            }
        }


    }
}

