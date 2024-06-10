package com.example.appclockin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonNavigateToTaskEntry: Button
    private lateinit var buttonNavigateToViewEntry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find buttons by their IDs
        buttonNavigateToTaskEntry = findViewById(R.id.buttonNavigateToTaskEntry)
        buttonNavigateToViewEntry = findViewById(R.id.buttonNavigateToViewEntry)

        // Set click listener for buttonNavigateToTaskEntry
        buttonNavigateToTaskEntry.setOnClickListener {
            // Navigate to TaskEntryActivity
            val intent = Intent(this, TaskEntryActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for buttonNavigateToViewEntry
        buttonNavigateToViewEntry.setOnClickListener {
            // Navigate to DisplayUserActivity
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}
