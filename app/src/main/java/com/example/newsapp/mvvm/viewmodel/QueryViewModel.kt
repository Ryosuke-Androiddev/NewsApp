package com.example.newsapp.mvvm.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.mvvm.db.DataStoreRepository
import com.example.newsapp.mvvm.utility.Constants.Companion.API_KEY
import com.example.newsapp.mvvm.utility.Constants.Companion.COUNTRY_CODE
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_CATEGORY
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_COUNTRY
import com.example.newsapp.mvvm.utility.Constants.Companion.NEWS_CATEGORY
import com.example.newsapp.mvvm.utility.Constants.Companion.QUERY_VALUE_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueryViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
): AndroidViewModel(application) {

    // Check Network Value
    var networkStatus = false

    var backOnline = false

    private var newsCategory = DEFAULT_NEWS_CATEGORY
    private var newsCountry = DEFAULT_NEWS_COUNTRY

    /** setup for DataStoreRepository and Tips*/

    val readNewsTipsType = dataStoreRepository.readNewsTipsType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveNewsTipsType(newsCategory: String,newsCategoryId: Int,newsCountry: String,newsCountryId: Int) =
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepository.saveNewsTipsType(newsCategory,newsCategoryId,newsCountry,newsCountryId)
            }

    fun saveBackOnline(backOnline: Boolean)=
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepository.saveBackOnline(backOnline)
            }

    /** setup for bottomSheet search function*/


    fun applyQueries(): HashMap<String,String>{

        viewModelScope.launch {
            readNewsTipsType.collect { value->
                newsCategory = value.selectedNewsCategory
                newsCountry = value.selectedNewsCountry
            }
        }

        val queries: HashMap<String,String> = HashMap()


        queries[COUNTRY_CODE] = newsCountry
        queries[NEWS_CATEGORY] = newsCategory

        //queries["country"] = "us"
        queries[QUERY_VALUE_API_KEY] = API_KEY

        return queries
    }

    fun applySearchQuery(searchQuery: String):HashMap<String,String>{
        val queries: HashMap<String,String> = HashMap()

        queries[COUNTRY_CODE] = searchQuery
        queries[QUERY_VALUE_API_KEY] = API_KEY

        return queries
    }

    fun showNetworkStatus(){
        if (!networkStatus){
            Toast.makeText(getApplication(),"No Internet Connection!!", Toast.LENGTH_LONG).show()
            saveBackOnline(true)
        }else if (networkStatus){
            Toast.makeText(getApplication(),"We're back online!!", Toast.LENGTH_LONG).show()
            saveBackOnline(false)
        }
    }
}