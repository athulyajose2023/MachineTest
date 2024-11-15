package com.example.appadoreee.activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appadoreee.R

class ResultActivity : AppCompatActivity() {

    private lateinit var gameOverText: TextView
    private lateinit var scoreText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        gameOverText = findViewById(R.id.gameOverText)
        scoreText = findViewById(R.id.scoreText)

        val score = intent.getIntExtra("score", 0)
        scoreText.text = "Score: $score/15"  // Assume 15 is the total number of questions

    }
}