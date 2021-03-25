package com.example.newsapp.mvvm.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.mvvm.models.NewsResponse
import com.example.newsapp.mvvm.repository.NewsRepository
import com.example.newsapp.mvvm.utility.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    application: Application
): AndroidViewModel(application){

    var newsResponse: MutableLiveData<NetworkResult<NewsResponse>> = MutableLiveData()

    fun getInformation(queries: Map<String,String>) = viewModelScope.launch {
        getNewsSafeCall(queries)
    }

    private suspend fun getNewsSafeCall(queries: Map<String, String>) {
        newsResponse.value = NetworkResult.Loading()
        if (checkInternetConnection()){
            try {
                val response = repository.remote.getBreakingNews(queries)
                newsResponse.value = handleNewsResponse(response)
            } catch (e:Exception){
                newsResponse.value = NetworkResult.Error("Not found")
            }
        } else{
            newsResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    //return values will be nullable so, you should write ? after that
    private fun handleNewsResponse(response: Response<NewsResponse>): NetworkResult<NewsResponse>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.articles.isNullOrEmpty() -> {
                NetworkResult.Error("Not found.")
            }
            response.isSuccessful -> {
                val newsInformation = response.body()
                NetworkResult.Success(newsInformation!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun checkInternetConnection():  Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}