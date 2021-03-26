package com.example.newsapp.mvvm.db


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.mvvm.models.Source
import com.example.newsapp.mvvm.utility.Constants.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = TABLE_NAME
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)