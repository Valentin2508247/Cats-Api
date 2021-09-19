package com.valentin.catsapi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.valentin.catsapi.models.Cat

@Dao
interface CatDao {
    @Query("Select * from cat")
    fun getCats(): LiveData<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCat(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(cats: List<Cat>)
}