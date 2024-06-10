package com.example.appclockin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appclockin.databinding.ActivityTaskEntryBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TaskEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskEntryBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance()
        firebaseRef = database.getReference("taskentry")

        binding.buttonSaveTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_add_task, null)
        val title = view.findViewById<EditText>(R.id.dialogTitle)
        val description = view.findViewById<EditText>(R.id.dialogDescription)
        val date = view.findViewById<EditText>(R.id.dialogDate)
        val startTime = view.findViewById<EditText>(R.id.dialogStartTime)
        val endTime = view.findViewById<EditText>(R.id.dialogEndTime)
        val category = view.findViewById<Spinner>(R.id.category)
        val userId = view.findViewById<EditText>(R.id.dialogUserId)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val titleText = title.text.toString().trim()
                val descriptionText = description.text.toString().trim()
                val dateText = date.text.toString().trim()
                val startTimeText = startTime.text.toString().trim()
                val endTimeText = endTime.text.toString().trim()
                val categoryValue = category.selectedItemPosition + 1
                val picture = when (categoryValue) {
                    1 -> "a.png"
                    2 -> "b.png"
                    3 -> "c.png"
                    else -> ""
                }
                val userIdText = userId.text.toString().trim()

                if (titleText.isEmpty() || descriptionText.isEmpty() || dateText.isEmpty() ||
                    startTimeText.isEmpty() || endTimeText.isEmpty() || userIdText.isEmpty()
                ) {
                    Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val taskEntry = TaskEntry(
                    title = titleText,
                    description = descriptionText,
                    date = dateText,
                    startTime = startTimeText,
                    endTime = endTimeText,
                    category = categoryValue,
                    picture = picture,
                    userId = userIdText
                )

                firebaseRef.child(userIdText).push().setValue(taskEntry)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, DisplayUserActivity::class.java))
                            Toast.makeText(this, "Task added successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Failed to add task", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Task addition canceled", Toast.LENGTH_SHORT).show()
            }
            .create()

        dialog.show()
    }

    data class TaskEntry(
        val title: String  ?= null,
        val description: String ?= null,
        val date: String ?= null,
        val startTime: String ?= null,
        val endTime: String ?= null,
        val category: Int ?= null,
        val picture: String ?= null,
        val userId: String ?= null
    )
}
