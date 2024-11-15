package com.example.appadoreee.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appadoreee.R

class CountdownActivity : AppCompatActivity() {
    private lateinit var countdownText: TextView
    private lateinit var timerText: TextView
    private var remainingTime = 20 // 20 seconds for countdown
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        countdownText = findViewById(R.id.countdownText)
        timerText = findViewById(R.id.timerText)

        val countDownTimer = object : CountDownTimer(remainingTime * 1000L, 1000) {


            override fun onFinish() {
                val intent = Intent(this@CountdownActivity, QuizActivity::class.java)
                startActivity(intent)
                finish()
            }
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerText.text = secondsLeft.toString()
            }
        }

        countDownTimer.start()
    }
}