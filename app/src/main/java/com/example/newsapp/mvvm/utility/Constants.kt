package com.example.newsapp.mvvm.utility

import com.example.newsapp.BuildConfig

class Constants {

    companion object{
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://newsapi.org"
        const val DATABASE_NAME = "article_database"
        const val TABLE_NAME = "articles_table"

        //        countryCode: String = "us",
        //        pageNumber: Int = 1,
        //        apiKey: String = Constants.API_KEY
        const val COUNTRY_CODE = "countryCode"
        const val PAGE_NUMBER = "pageNumber"
        const val QUERY_API_KEY = API_KEY
    }

}