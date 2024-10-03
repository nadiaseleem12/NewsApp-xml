package com.example.news.data.api.model.articlesModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.news.data.api.model.sourcesModel.Source
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
@Entity(foreignKeys = [ForeignKey(Source::class, parentColumns = ["id"], childColumns = ["sourceId"], onDelete = ForeignKey.CASCADE)])
@Parcelize
data class Article(
    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    var sourceId:String?=null,

    @PrimaryKey(autoGenerate = true)
    val id:Int=0

) : Parcelable{

    @IgnoredOnParcel
    @Ignore
    @field:SerializedName("source")
    val source: Source? = null
}
