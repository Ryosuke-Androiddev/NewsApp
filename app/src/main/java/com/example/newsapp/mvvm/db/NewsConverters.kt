package com.example.newsapp.mvvm.db

import androidx.room.TypeConverter
import com.example.newsapp.mvvm.models.Article
import com.example.newsapp.mvvm.models.NewsResponse
import com.example.newsapp.mvvm.models.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsConverters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name,name)
    }

    private var gson = Gson()

    @TypeConverter
    fun newsResponseToString(newsResponse: NewsResponse): String{
        return gson.toJson(newsResponse)
    }

    @TypeConverter
    fun stringToNewsResponse(data: String): NewsResponse {
        val listType = object : TypeToken<NewsResponse>() {}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverter
    fun articleToString(article: Article): String{
        return gson.toJson(article)
    }

    @TypeConverter
    fun stringToArticle(data: String): Article{
        val listType = object : TypeToken<NewsResponse>() {}.type
        return gson.fromJson(data,listType)
    }
}