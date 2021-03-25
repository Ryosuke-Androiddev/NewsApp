package com.example.newsapp.mvvm.api

import com.example.newsapp.mvvm.models.NewsResponse
import com.example.newsapp.mvvm.utility.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
       @QueryMap queries: Map<String,String>
    ): Response<NewsResponse>

    //        @Query("country")
    //        countryCode: String = "us",
    //        @Query("page")
    //        pageNumber: Int = 1,
    //        @Query("apiKey")
    //        apiKey: String = API_KEY

    @GET("v2/everything")
    suspend fun searchForNews(
        @QueryMap searchQuery: Map<String,String>
    ): Response<NewsResponse>

    //        @Query("q")
    //        countryCode: String,
    //        @Query("page")
    //        pageNumber: Int = 1,
    //        @Query("apiKey")
    //        apiKey: String = API_KEY
}