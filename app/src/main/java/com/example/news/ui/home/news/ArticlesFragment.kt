package com.example.news.ui.home.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.news.R
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source
import com.example.news.databinding.FragmentArticlesBinding
import com.example.news.ui.home.MainActivity
import com.example.news.util.TabSharedPreferences
import com.example.news.util.handleError
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private lateinit var binding: FragmentArticlesBinding

    @Inject
    lateinit var adapter: ArticlesAdapter
    private val viewModel: ArticlesViewModel by viewModels()
    private val args by navArgs<ArticlesFragmentArgs>()
    private lateinit var currentSource:String
    private lateinit var tabSharedPreferences: TabSharedPreferences

    private var retry : (()->Unit)? = null
    private val receiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action==ConnectivityManager.CONNECTIVITY_ACTION){
               viewModel.updateConnectivity()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(receiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        binding = FragmentArticlesBinding.inflate(inflater, container, false)

        tabSharedPreferences = TabSharedPreferences(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.getSources()
        initObservers()
        initRecyclerView()
        initInternetCheckerObserver()
    }

    private fun initInternetCheckerObserver() {
        viewModel.isConnected.observe(viewLifecycleOwner){isConnected->

            if (!isConnected){
                binding.interntStatusTv.text = getString(R.string.no_connection)
                binding.interntStatusTv.setBackgroundColor(resources.getColor(R.color.grey))
                binding.interntStatusTv.isVisible = true
            }else if (viewModel.shouldShowBackOnline()){
                binding.interntStatusTv.text = getString(R.string.back__online)
                binding.interntStatusTv.setBackgroundColor(resources.getColor(R.color.green))
                binding.interntStatusTv.isVisible = true

                retry?.invoke()

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.interntStatusTv.isVisible = false
                },2000)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).setCustomToolbarTitle(args.category.replaceFirstChar { it.uppercase() })
        setSelectedTabFromSharedPreferences()
    }

    private fun setSelectedTabFromSharedPreferences() {
        val selectedTab = tabSharedPreferences.getSelectedTab()
        binding.tabLayout.getTabAt(selectedTab)?.select()

    }

    private fun initObservers() {
        viewModel.uiMessage.observe(viewLifecycleOwner) { uiMessage ->
            uiMessage.errorMessage?.let {
                handleError(message = it) {
                    uiMessage.posAction
                }
            }
            uiMessage.posAction?.let {
                retry = it
            }

        }
        viewModel.articlesList.observe(viewLifecycleOwner) { articles ->
            adapter.updateArticles(articles as MutableList<Article>)
        }
        viewModel.sourcesList.observe(viewLifecycleOwner) { sources ->
            bindTabs(sources)
        }

    }


    private fun initRecyclerView() {

        binding.articlesRv.adapter = adapter
        adapter.onArticleClick = { article: Article ->
            navigateToArticleDetailsFragment(article)
        }
        adapter.onReachedBottom = {
            viewModel.getArticles(currentSource)
        }
    }

    private fun navigateToArticleDetailsFragment(article: Article) {
        article.title?.let {
            val direction = ArticlesFragmentDirections.navigateToArticleDetailsFragment(it)
            findNavController().navigate(direction)
        }

    }


    private fun bindTabs(sources: List<Source?>?) {
        if (sources == null)
            return
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source?.id
            binding.tabLayout.addTab(tab)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    tabSharedPreferences.saveSelectedTab(it)
                }

                currentSource =  tab?.tag.toString()
                viewModel.getArticles(
                    currentSource
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                currentSource =  tab?.tag.toString()
                viewModel.getArticles(
                    currentSource
                )
            }
        })

        setSelectedTabFromSharedPreferences()


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {

                val direction = ArticlesFragmentDirections.navigateToSearchFragment()
                findNavController().navigate(direction)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
