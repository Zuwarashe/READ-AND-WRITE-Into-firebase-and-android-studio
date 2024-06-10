package com.example.appclockin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel  : ViewModel() {

    private val repository : Repos
    private val _allUsers = MutableLiveData<List<TaskEntry>>()
    val allUsers : LiveData<List<TaskEntry>> = _allUsers


    init {

        repository = Repos().getInstance()
        repository.loadUsers(_allUsers)

    }

}