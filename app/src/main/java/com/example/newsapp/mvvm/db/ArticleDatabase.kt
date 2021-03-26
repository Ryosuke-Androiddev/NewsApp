package com.example.newsapp.mvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NewsConverters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}