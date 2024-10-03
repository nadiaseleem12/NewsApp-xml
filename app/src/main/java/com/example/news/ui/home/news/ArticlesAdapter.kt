package com.example.news.ui.home.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.databinding.ItemArticleBinding

class ArticlesAdapter(var articles: MutableList<Article>? = mutableListOf()) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(article: Article) {
            binding.article = article
            binding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = articles?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles?.get(position)!!
        holder.bind(article)

        if (onArticleClick != null) {
            holder.itemView.setOnClickListener {
                onArticleClick?.invoke(article)
            }
        }


        if (position== articles!!.size-1){
            onReachedBottom?.let {
                it()
            }
        }
    }

    fun updateArticles(articles: MutableList<Article>) {
        if (articles.isEmpty()){
            this.articles=articles
        }else if (this.articles.isNullOrEmpty()){
            this.articles=articles
        }else if (articles[0].source?.id == this.articles!![0].source?.id){
            this.articles!!.addAll(articles)
        }else{
            this.articles=articles
        }

        notifyDataSetChanged()
    }

    var onArticleClick: ((article: Article) -> Unit)? = null
    var onReachedBottom:(()->Unit)?=null
}
