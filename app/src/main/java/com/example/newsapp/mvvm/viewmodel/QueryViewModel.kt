package com.example.newsapp.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.newsapp.BuildConfig
import com.example.newsapp.mvvm.utility.Constants.Companion.API_KEY
import com.example.newsapp.mvvm.utility.Constants.Companion.COUNTRY_CODE
import com.example.newsapp.mvvm.utility.Constants.Companion.PAGE_NUMBER
import com.example.newsapp.mvvm.utility.Constants.Companion.QUERY_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QueryViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {

    fun applyQueries(): HashMap<String,String>{

        val queries: HashMap<String,String> = HashMap()

        queries[COUNTRY_CODE] = "us"
        queries[PAGE_NUMBER] = "1"
        queries[QUERY_API_KEY] = API_KEY

        return queries
    }
}