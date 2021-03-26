package com.example.newsapp.mvvm.db.datasource

import com.example.newsapp.mvvm.api.NewsApi
import com.example.newsapp.mvvm.models.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getBreakingNews(
        queries: Map<String,String>
    ): Response<NewsResponse>{
        return newsApi.getBreakingNews(queries)
    }

    //        countryCode: String = "us",
    //        pageNumber: Int = 1,
    //        apiKey: String = Constants.API_KEY

    suspend fun searchForNews(
        searchQuery: Map<String,String>
    ): Response<NewsResponse>{
        return newsApi.searchForNews(searchQuery)
    }

    //        countryCode: String,
    //        pageNumber: Int = 1,
    //        apiKey: String = Constants.API_KEY
}