package com.example.appclockin

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Repos {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("taskentry")

    @Volatile private var INSTANCE : Repos ?= null

    fun getInstance() : Repos{
        return INSTANCE ?: synchronized(this){

            val instance = Repos()
            INSTANCE = instance
            instance
        }


    }

    fun loadUsers(userList : MutableLiveData<List<TaskEntry>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {

                    val _userList : List<TaskEntry> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(TaskEntry::class.java)!!

                    }

                    userList.postValue(_userList)

                }catch (e : Exception){


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}