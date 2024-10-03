package com.example.news.ui.home.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.databinding.FragmentSearchBinding
import com.example.news.ui.home.MainActivity
import com.example.news.ui.home.news.ArticlesAdapter
import com.example.news.util.handleError
import com.example.news.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    @Inject
    lateinit var adapter: ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initObservers()
        initRV()
    }

    private fun initRV() {
        binding.rvSearchedArticles.adapter = adapter
        adapter.onArticleClick = {article: Article ->
            article.title?.let {
                val direction = SearchFragmentDirections.navigateToArticleDetailsFragment(it)
                findNavController().navigate(direction)

            }

        }
    }

    private fun initObservers() {
        viewModel.uiMessage.observe(viewLifecycleOwner) { uiMessage ->

            uiMessage.errorMessage?.let {
                handleError(message = it) {
                    uiMessage.posAction
                }
            }
            uiMessage.errorMessageId?.let {
                handleError(getString(it)) {
                    uiMessage.posAction
                }
            }

            uiMessage.posAction?.let { posAction ->
                handleError { posAction }
            }
        }
        viewModel.articlesList.observe(viewLifecycleOwner) { articles ->
            adapter.updateArticles(articles as MutableList<Article>)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
       val item =  menu.findItem(R.id.action_search)
        item.expandActionView()
        item.isChecked = true
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getArticles(query)
                    view?.hideKeyboard(requireContext())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }

        })


        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                (requireActivity() as MainActivity).setCustomToolbarTitle("Search")

                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                Handler(Looper.getMainLooper()).postDelayed({
                    (requireActivity() as MainActivity).setCustomToolbarTitle("Search")
                },100)


                return true
            }

        })


    }
}