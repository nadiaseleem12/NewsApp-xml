package com.example.news.ui.home.articleDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.news.R
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.databinding.FragmentArticleDetailsBinding
import com.example.news.ui.home.MainActivity
import com.example.news.ui.home.news.ArticlesFragmentArgs
import com.example.news.util.handleError
import com.example.news.util.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {
    private val viewModel by viewModels<ArticleDetailsViewModel>()
    private val args by navArgs<ArticleDetailsFragmentArgs>()
    private lateinit var binding: FragmentArticleDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getArticle(args.title)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initObservers()

    }

    private fun initObservers() {
        viewModel.article.observe(viewLifecycleOwner){article->
            binding.articleReadMore.text=getString(R.string.view_full_article)
            onViewFullArticleClick(article)
            article.source?.name?.let { (requireActivity() as MainActivity).setCustomToolbarTitle(it) }
        }

        viewModel.uiMessage.observe(viewLifecycleOwner){uiMessage->
            uiMessage.errorMessage?.let{
                handleError(it) {
                    uiMessage.posAction
                }
                uiMessage.errorMessageId?.let {
                    handleError(getString(it)){
                        uiMessage.posAction
                    }
                }
            }

        }
    }

    private fun onViewFullArticleClick(article: Article) {
        binding.articleReadMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.url)
            startActivity(intent)
        }

    }

}

