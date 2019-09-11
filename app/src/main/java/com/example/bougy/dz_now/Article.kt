package com.example.bougy.dz_now

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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