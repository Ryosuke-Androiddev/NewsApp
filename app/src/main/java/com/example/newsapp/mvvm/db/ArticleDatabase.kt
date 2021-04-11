package com.example.newsapp.mvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.mvvm.db.entities.ArticleEntity
import com.example.newsapp.mvvm.db.entities.FavoriteEntity

@Database(
    entities = [ArticleEntity::class,FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NewsConverters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}