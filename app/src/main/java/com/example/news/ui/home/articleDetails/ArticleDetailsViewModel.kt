package com.example.news.ui.home.articleDetails

import android.accessibilityservice.GestureDescription.StrokeDescription
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.R
import com.example.news.data.api.WebServices
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.articlesModel.ArticlesResponse
import com.example.news.data.repository.NewsRepo
import com.example.news.util.UIMessage
import com.example.news.util.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(val newsRepo: NewsRepo):ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article :LiveData<Article> get()=_article


    private val _uiMessage = MutableLiveData<UIMessage>()
    val uiMessage: LiveData<UIMessage> get() = _uiMessage

    fun getArticle(title:String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _uiMessage.postValue(UIMessage(isLoading = true))
                    _article.postValue( newsRepo.getArticle(title=title))
                _uiMessage.postValue(UIMessage(isLoading = false))
            }catch (e: retrofit2.HttpException) {
                val articlesResponse = e.response()?.errorBody()?.string()?.fromJson(
                    ArticlesResponse::class.java
                )
                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessage = articlesResponse?.message,
                        posAction = {
                            getArticle(title)
                        })
                )

            } catch (e: IOException) {
                //UnknownHostException
                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessageId = R.string.connection_error,
                        posAction = {
                            getArticle(title)
                        })
                )
            } catch (e: Exception) {
                _uiMessage.postValue(
                    UIMessage(
                        isLoading = false,
                        errorMessage = e.localizedMessage,
                        posAction = {
                            getArticle(title)
                        })
                )
            }



        }
    }

}
