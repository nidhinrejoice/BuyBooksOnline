package com.nidhin.customerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nidhin.customerapp.persistance.Converters

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey
    val section: String,

    @TypeConverters(Converters::class)
    var catIds: List<Int>
)
