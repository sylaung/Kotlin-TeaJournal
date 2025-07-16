package com.syla.teajournal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TeaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tea: Tea)

    @Update
    suspend fun update(tea: Tea)

    @Delete
    suspend fun delete(tea: Tea)

    @Query("SELECT * FROM tea_table ORDER BY id ASC")
    fun getAllTeas(): Flow<List<Tea>>

    @Query("SELECT * FROM tea_table WHERE id = :id")
    fun getTeaById(id: Int): Flow<Tea>
}