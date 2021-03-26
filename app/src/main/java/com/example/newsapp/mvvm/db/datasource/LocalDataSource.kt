package com.example.newsapp.mvvm.db.datasource

import com.example.newsapp.mvvm.db.ArticleDao
import com.example.newsapp.mvvm.db.ArticleEntity
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

    suspend fun deleteArticles(articleEntity: ArticleEntity){
        articleDao.deleteArticles(articleEntity)
    }
}