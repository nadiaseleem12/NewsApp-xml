package com.example.news.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.FragmentCategoriesBinding
import com.example.news.ui.home.MainActivity


class CategoriesFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCategoriesListeners()

    }

    private fun setCategoriesListeners() {
        binding.sportsCategory.setOnClickListener(this)
        binding.technologyCategory.setOnClickListener(this)
        binding.healthCategory.setOnClickListener(this)
        binding.businessCategory.setOnClickListener(this)
        binding.entertainmentCategory.setOnClickListener(this)
        binding.scienceCategory.setOnClickListener(this)
    }


    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).setCustomToolbarTitle(getString(R.string.app_name))
    }


    override fun onClick(v: View?) {
        val direction =
            CategoriesFragmentDirections.navigateToArticlesFragment(category = v?.tag.toString())
        findNavController().navigate(direction)
    }

}