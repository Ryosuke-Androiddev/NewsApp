package com.example.newsapp.mvvm.repository

import com.example.newsapp.mvvm.api.NewsApi
import com.example.newsapp.mvvm.db.ArticleDao
import com.example.newsapp.mvvm.db.ArticleDatabase
import com.example.newsapp.mvvm.db.datasource.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NewsRepository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    //ViewModel can access RemoteDataSource because this remote is useful for it!!
    val remote = remoteDataSource
}