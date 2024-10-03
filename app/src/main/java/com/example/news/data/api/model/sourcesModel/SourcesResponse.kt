package com.example.news.data.api.model.sourcesModel

import android.os.Parcelable
import com.example.news.data.api.model.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourcesResponse(
    @field:SerializedName("sources")
    val sources: List<Source>? = null,


    ) : BaseResponse(), Parcelable