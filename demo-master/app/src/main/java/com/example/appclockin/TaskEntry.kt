package com.example.appclockin

data class TaskEntry(
    val title: String ?= null,
    val description: String ?= null,
    val date: String ?= null,
    val startTime: String ?= null,
    val endTime: String ?= null,
    val category: Int ?= null,
    val picture: String ?= null,
    val userId: String ?= null
)
