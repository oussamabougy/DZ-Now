package com.example.bougy.dz_now

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Article (
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val body:String,
    @ColumnInfo val image_url:String,
    @ColumnInfo val date: String,
    @ColumnInfo val category: String,
    @ColumnInfo val lien:String,
    @ColumnInfo val source:String
) : Serializable