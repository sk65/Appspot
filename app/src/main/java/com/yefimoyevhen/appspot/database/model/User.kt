package com.yefimoyevhen.appspot.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val age: Int,
    val country: String,
    val firstName: String,
    val gender: String,
    val lastName: String
)