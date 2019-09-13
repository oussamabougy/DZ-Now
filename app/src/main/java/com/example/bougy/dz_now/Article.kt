package com.example.bougy.dz_now

import androidx.room.*
import java.io.Serializable

@Entity
data class Article (
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val body:String,
    @ColumnInfo val image_url:String,
    @ColumnInfo val date: String,
    @Embedded(prefix = "category_") val category: Categorie,
    @ColumnInfo val link:String,
    @Embedded(prefix = "source_") val source:Source
) : Serializable