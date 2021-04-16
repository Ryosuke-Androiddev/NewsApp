package com.example.newsapp.mvvm.db

import androidx.room.*
import com.example.newsapp.mvvm.db.entities.ArticleEntity
import com.example.newsapp.mvvm.db.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    /** setup for BreakingNewsFragment */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    /** setup for savedNewsFragment */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table ORDER BY id ASC")
    fun readFavoriteNews(): Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteNews(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavoriteNews()
}