package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class ConditionEntity(
    @ColumnInfo(name="code")
    val code: Int,
    @ColumnInfo(name="icon")
    val icon: String,
    @ColumnInfo(name="text")
    val text: String

)
