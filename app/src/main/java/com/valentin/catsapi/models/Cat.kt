package com.valentin.catsapi.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cat")
data class Cat(
    @PrimaryKey val id: String,
    val url: String
    ): Parcelable
