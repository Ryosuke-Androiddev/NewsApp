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
        const val COUNTRY_CODE = "country"
        const val PAGE_NUMBER = "pageNumber"
        const val NEWS_CATEGORY = "category"
        const val QUERY_API_KEY = API_KEY
        // this may be right, but if you have any problems you modify here first!

        //setup for chip
        const val DEFAULT_NEWS_CATEGORY = "general"
        const val DEFAULT_NEWS_COUNTRY = "us"
        const val DEFAULT_NEWS_PAGE = "1"

        //setup for dataStore
        const val PREFERENCES_NAME = "news_preferences"
        const val PREFERENCES_NEWS_CATEGORY = "newsCategory"
        const val PREFERENCES_NEWS_CATEGORY_ID = "newsCategoryId"
        const val PREFERENCES_NEWS_COUNTRY = "newsCountry"
        const val PREFERENCES_NEWS_COUNTRY_ID = "newsCountryId"
        const val DEFAULT_NEWS_CHIP_ID = 0

        //modify constants
        const val QUERY_VALUE_API_KEY = "apiKey"
    }

}