package com.example.newsApp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsApp.constants.Constants
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles


    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines(category : String = "GENERAL") {

        val newsApiClient = NewsApiClient(Constants.API_KEY)
        val request = TopHeadlinesRequest
            .Builder()
            .language("en")
            .category(category)
            .build()
        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
               response?.articles?.let { article ->
                   _articles.postValue(article)
               }
            }

            override fun onFailure(throwable: Throwable?) {
                throwable?.localizedMessage?.let { Log.e("dorrriEr", it.toString()) }
            }

        })
    }

    fun fetchWithQuery(query : String) {

        val newsApiClient = NewsApiClient(Constants.API_KEY)
        val request = EverythingRequest
            .Builder()
            .language("en")
            .q(query)
            .build()
        newsApiClient.getEverything(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let { article ->
                    _articles.postValue(article)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                throwable?.localizedMessage?.let { Log.e("dorrriEr", it.toString()) }
            }

        })
    }
}