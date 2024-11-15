package com.example.appadoreee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appadoreee.activity.CountdownActivity
import com.example.appadoreee.ui.theme.AppadoreeeTheme

class MainActivity : ComponentActivity() {
    private lateinit var timePicker: TimePicker
    private lateinit var saveButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        timePicker = findViewById(R.id.timePicker)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            val intent = Intent(this, CountdownActivity::class.java)
            intent.putExtra("hour", hour)
            intent.putExtra("minute", minute)
            startActivity(intent)
        }

    }
}

