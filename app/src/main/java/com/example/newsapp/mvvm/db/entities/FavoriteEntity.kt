package com.example.newsapp.mvvm.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.mvvm.models.Article
import com.example.newsapp.mvvm.utility.Constants.Companion.FAVORITE_DB_NAME


@Entity(tableName = FAVORITE_DB_NAME)
data class FavoriteEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var article: Article
)