package com.example.bougy.dz_now

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Source (
    @PrimaryKey val id: Int,
    @ColumnInfo val name:String
): Serializable