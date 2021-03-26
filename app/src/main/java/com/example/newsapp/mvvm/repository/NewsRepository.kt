package com.example.newsapp.mvvm.repository


import com.example.newsapp.mvvm.db.datasource.LocalDataSource
import com.example.newsapp.mvvm.db.datasource.RemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class NewsRepository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    //ViewModel can access RemoteDataSource because this remote is useful for it!!
    val remote = remoteDataSource

    val local = localDataSource
}