package com.example.appclockin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.*

class DisplayUserActivity : AppCompatActivity() {

    private lateinit var username : EditText
    private lateinit var btn: Button
    private lateinit var first: TextView
    private lateinit var second: TextView
    private lateinit var third : TextView
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user)

        username = findViewById(R.id.nameInput)
        btn = findViewById(R.id.searchButton)
        first = findViewById(R.id.nameField)
        second = findViewById(R.id.emailField)
        third = findViewById(R.id.passwordField)

        btn.setOnClickListener(View.OnClickListener  {

            val usern = username.text.toString()
            database = FirebaseDatabase.getInstance().getReference("taskentry")
            database.child(usern).get().addOnSuccessListener {
                if (it.exists()){

                    val firstn = it.child("title").value
                    val emailn = it.child("description").value
                    val passn = it.child("date").value

                    Toast.makeText(this, "Success",Toast.LENGTH_LONG).show()

                    first.text = "Name"+firstn.toString()
                    second.text = "Email"+emailn.toString()
                    third.text = "Password"+passn.toString()

                    username.text.clear()

                }
            }.addOnFailureListener{
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }



}
