package com.azachos.weatherapp.datapersistence.entities

import androidx.room.ColumnInfo

data class LocationEntity(
    @ColumnInfo(name="country")
    val country: String,
    @ColumnInfo(name="localtime")
    val localtime: String,
    @ColumnInfo(name="localtime_epoch")
    val localtimeEpoch: Int,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="region")
    val region: String,
    @ColumnInfo(name="tz_id")
    val tzId: String
)
