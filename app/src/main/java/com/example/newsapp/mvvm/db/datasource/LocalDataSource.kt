package com.example.newsapp.mvvm.db.datasource

import com.example.newsapp.mvvm.db.ArticleDao
import com.example.newsapp.mvvm.db.entities.ArticleEntity
import com.example.newsapp.mvvm.db.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val articleDao: ArticleDao
){
    suspend fun insertNews(articleEntity: ArticleEntity){
        articleDao.insertNews(articleEntity)
    }

    fun getAllArticles(): Flow<List<ArticleEntity>>{
        return articleDao.getAllArticles()
    }

    /** setup for SavedNewsFragment */

    fun readFavoriteNews(): Flow<List<FavoriteEntity>>{
        return articleDao.readFavoriteNews()
    }

    suspend fun insertFavoriteNews(favoriteEntity: FavoriteEntity){
        articleDao.insertFavoriteNews(favoriteEntity)
    }

    suspend fun deleteFavoriteNews(favoriteEntity: FavoriteEntity){
        articleDao.deleteFavoriteNews(favoriteEntity)
    }

    suspend fun deleteAllFavoriteNews(){
        articleDao.deleteAllFavoriteNews()
    }
}