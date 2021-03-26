package com.example.newsapp.mvvm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.mvvm.models.NewsResponse
import com.example.newsapp.mvvm.utility.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class ArticleEntity(
    var newsResponse: NewsResponse
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}