package com.valentin.catsapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class Cat(
    @PrimaryKey val id: String,
    val url: String)

