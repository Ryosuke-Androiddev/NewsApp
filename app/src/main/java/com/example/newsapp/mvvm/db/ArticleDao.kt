package com.example.newsapp.mvvm.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Delete
    suspend fun deleteArticles(articleEntity: ArticleEntity)
}