package com.example.appadoreee.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.appadoreee.R
import com.example.appadoreee.model.Country
import com.example.appadoreee.model.Question

class QuizActivity : AppCompatActivity() {

    private lateinit var questionNumber: TextView
    private lateinit var questionTitle: TextView
    private lateinit var flagImage: ImageView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button
    private lateinit var timerText: TextView

    private var questionIndex = 0
    private var questions: List<Question> = listOf()
    private var score = 0
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Initialize views
        questionNumber = findViewById(R.id.questionNumber)
        questionTitle = findViewById(R.id.questionTitle)
        flagImage = findViewById(R.id.flagImage)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        timerText = findViewById(R.id.timerText)

        // Load mock questions
        loadQuestions()

        // Start the first question
        setQuestion()

        // Button click listeners
        option1.setOnClickListener { checkAnswer(option1) }
        option2.setOnClickListener { checkAnswer(option2) }
        option3.setOnClickListener { checkAnswer(option3) }
        option4.setOnClickListener { checkAnswer(option4) }
    }

    private fun loadQuestions() {
        questions = listOf(
            Question(
                answer_id = 160,
                countries = listOf(
                    Country("New Zealand", 160),
                    Country("Chile", 45),
                    Country("Mauritania", 142),
                    Country("Bosnia and Herzegovina", 29)
                ),
                country_code = "NZ"
            ),
            Question(
                answer_id = 13,
                countries = listOf(
                    Country("Ecuador", 66),
                    Country("Bhutan", 26),
                    Country("Aruba", 13),
                    Country("Serbia", 184)
                ),
                country_code = "EC"
            )
        )
    }

    private fun setQuestion() {
        if (questionIndex >= questions.size) {
            showResult()
            return
        }

        val question = questions[questionIndex]
        questionNumber.text = "Question ${questionIndex + 1}/${questions.size}"
        questionTitle.text = "Guess the Country by the Flag"

        // Load flag image
        val flagUrl = "https://flagcdn.com/w320/${question.country_code.lowercase()}.png"
        Glide.with(this)
            .load(flagUrl)
            .placeholder(R.drawable.img)
            .error(R.drawable.ic_launcher_background)
            .into(flagImage)

        // Reset button states
        resetButtonStates()

        // Set options
        option1.text = question.countries[0].country_name
        option2.text = question.countries[1].country_name
        option3.text = question.countries[2].country_name
        option4.text = question.countries[3].country_name

        // Start the timer
        startTimer(30)
    }

    private fun resetButtonStates() {
        option1.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
        option2.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
        option3.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
        option4.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
    }

    private fun checkAnswer(selectedOption: Button) {
        val question = questions[questionIndex]
        val correctCountry = question.countries.firstOrNull { it.id == question.answer_id }

        // Validate the correct answer
        if (correctCountry == null) {
            Toast.makeText(this, "Error: Missing correct answer data", Toast.LENGTH_SHORT).show()
            return
        }

        // Check user's answer
        if (selectedOption.text == correctCountry.country_name) {
            score++
            selectedOption.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            selectedOption.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            Toast.makeText(this, "Wrong! Correct answer: ${correctCountry.country_name}", Toast.LENGTH_SHORT).show()
        }

        // Move to the next question after a short delay
        timer?.cancel()
        selectedOption.postDelayed({
            questionIndex++
            setQuestion()
        }, 1000)
    }

    private fun showResult() {
        timer?.cancel()
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("score", score)
        }
        startActivity(intent)
        finish()
    }

    private fun startTimer(seconds: Int) {
        timer?.cancel()
        timer = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerText.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@QuizActivity, "Time's up!", Toast.LENGTH_SHORT).show()
                checkAnswer(option1) // Automatically submit the first option
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
