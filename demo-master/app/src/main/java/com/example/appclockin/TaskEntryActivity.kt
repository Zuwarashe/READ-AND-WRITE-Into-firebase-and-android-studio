package com.example.appclockin



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appclockin.databinding.ActivityTaskEntryBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TaskEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskEntryBinding
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEntryBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_task_entry)

        // Initialize Firebase
        firebaseRef = FirebaseDatabase.getInstance().getReference("taskentry")

        binding.buttonSaveTask.setOnClickListener {
            Toast.makeText(this, "we heard the click baba", Toast.LENGTH_SHORT).show()
            val title = binding.title.text.toString().trim()
            val description = binding.description.text.toString().trim()
            val date = binding.date.text.toString().trim()
            val startTime = binding.startTime.text.toString().trim()
            val endTime = binding.endTime.text.toString().trim()
            val category = binding.category.selectedItemPosition + 1
            val picture = "a.png"
            //when (category) {
         //       1 -> "a.png"
         //       2 -> "b.png"
         //       3 -> "c.png"
         //       else -> ""
          //  }
            val userId = binding.userId.text.toString().trim()

            // Validate input
            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || userId.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val taskEntry = TaskEntry(title, description, date, startTime, endTime, category, picture, userId)
            firebaseRef.child(userId).setValue(taskEntry)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, DisplayUserActivity::class.java))
                        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Failed to save task", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    data class TaskEntry(
        val title: String,
        val description: String,
        val date: String,
        val startTime: String,
        val endTime: String,
        val category: Int,
        val picture: String,
        val userId: String
    )

}