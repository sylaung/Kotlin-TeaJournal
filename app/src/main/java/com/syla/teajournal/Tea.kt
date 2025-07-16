package com.syla.teajournal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tea_table")
data class Tea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Default value 0 for auto-generation

    @ColumnInfo(name = "tea_name")
    val name: String,

    @ColumnInfo(name = "tea_type")
    val type: String,

    @ColumnInfo(name = "brew_temp")
    val brewingTemperature: String,

    @ColumnInfo(name = "steep_time")
    val steepTime: String,

    @ColumnInfo(name = "rating")
    val rating: Int,

    @ColumnInfo(name = "notes")
    val notes: String,

    @ColumnInfo(name = "date_tasted")
    val dateTasted: Long
)