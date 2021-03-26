package com.example.newsapp.mvvm.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.newsapp.mvvm.db.ArticleEntity
import com.example.newsapp.mvvm.models.NewsResponse
import com.example.newsapp.mvvm.repository.NewsRepository
import com.example.newsapp.mvvm.utility.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    application: Application
): AndroidViewModel(application){

    /** setup for ROOM*/
    val getAllArticles: LiveData<List<ArticleEntity>> = repository.local.getAllArticles().asLiveData()

    private fun insertNews(articleEntity: ArticleEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertNews(articleEntity)
        }

    /** setup for Retrofit*/
    var newsResponses: MutableLiveData<NetworkResult<NewsResponse>> = MutableLiveData()

    fun getInformation(queries: Map<String,String>) = viewModelScope.launch {
        getNewsSafeCall(queries)
    }

    private suspend fun getNewsSafeCall(queries: Map<String, String>) {
        newsResponses.value = NetworkResult.Loading()
        if (checkInternetConnection()){
            try {
                val response = repository.remote.getBreakingNews(queries)
                newsResponses.value = handleNewsResponse(response)

                val bNewsResponse = newsResponses.value!!.data
                if(bNewsResponse != null){
                    offlineCacheNews(bNewsResponse)
                }
            } catch (e:Exception){
                newsResponses.value = NetworkResult.Error("Not found")
            }
        } else{
            newsResponses.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheNews(bNewsResponse: NewsResponse) {
        val articleEntity = ArticleEntity(bNewsResponse)
        insertNews(articleEntity)
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