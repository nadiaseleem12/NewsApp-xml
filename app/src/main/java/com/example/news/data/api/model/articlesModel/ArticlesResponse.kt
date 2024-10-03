package com.example.news.data.api.model.articlesModel

import android.os.Parcelable
import com.example.news.data.api.model.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlesResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<Article>? = null,

    ) : BaseResponse(), Parcelable