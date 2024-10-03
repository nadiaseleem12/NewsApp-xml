package com.example.news.ui.home.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.R
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.articlesModel.ArticlesResponse
import com.example.news.data.api.model.sourcesModel.Source
import com.example.news.data.api.model.sourcesModel.SourcesResponse
import com.example.news.data.connectivity.NetworkHandler
import com.example.news.data.repository.NewsRepo
import com.example.news.ui.home.BaseViewModel
import com.example.news.util.UIMessage
import com.example.news.util.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
const val PAGE_SIZE = 3
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    savedStateHandle: SavedStateHandle,
    networkHandler: NetworkHandler
) : BaseViewModel(networkHandler) {

    private val _uiMessage = MutableLiveData<UIMessage>()
    val uiMessage: LiveData<UIMessage> get() = _uiMessage

    private val _articlesList = MutableLiveData<List<Article?>?>()
    val articlesList: LiveData<List<Article?>?> get() = _articlesList

    private val _sourcesList = MutableLiveData<List<Source?>?>()
    val sourcesList: LiveData<List<Source?>?> get() = _sourcesList

    private val categoryParam = "category"

    private var category = savedStateHandle[categoryParam] ?: "general"


    var nextPage= 1
    fun getArticles(source: String,page:Int?=nextPage,pageSize:Int?= PAGE_SIZE) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiMessage.postValue(UIMessage(isLoading = true, requestingNextPage = true))

                val articles = newsRepo.getArticles(source,page, pageSize)
                _articlesList.postValue(articles)

                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        shouldDisplayNoArticlesFound = articles.isEmpty(),
                        posAction ={
                            getArticles(source)
                        }
                    )
                )
                nextPage++

                delay(1000)

            } catch (e: HttpException) {
                val articlesResponse = e.response()?.errorBody()?.string()?.fromJson(
                    ArticlesResponse::class.java
                )

                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessage = articlesResponse?.message,
                        posAction = {
                            getArticles(source,page, pageSize)
                        })
                )

            } catch (e: Exception) {
                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessage = e.localizedMessage,
                        posAction = {
                            getArticles(source,page, pageSize)
                        })
                )
            }finally {
                _uiMessage.postValue(UIMessage(requestingNextPage = false))
            }
        }

    }


    fun getSources() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiMessage.postValue(UIMessage(isLoading = true))

                val sources = newsRepo.getSources(category)
                _sourcesList.postValue(sources)

                _uiMessage.postValue(UIMessage(isLoading = false, posAction = {
                    getSources()
                } ))

            } catch (e: HttpException) {
                val sourcesResponse =
                    e.response()?.errorBody()?.string()?.fromJson(SourcesResponse::class.java)
                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessage = sourcesResponse?.message,
                        posAction = {
                            getSources()
                        })
                )

            } catch (e: Exception) {
                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessage = e.localizedMessage,
                        posAction = {
                            getSources()
                        })
                )
            }

        }

    }

}
