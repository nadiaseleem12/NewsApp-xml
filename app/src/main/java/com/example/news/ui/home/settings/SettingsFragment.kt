package com.example.news.ui.home.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.FragmentSettingsBinding
import com.example.news.ui.home.MainActivity
import com.example.news.util.Constants
import com.example.news.util.getCurrentDeviceLanguageCode
import com.example.news.util.recreateActivity

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialLanguageState()
        onLanguageTVClick()

    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).setCustomToolbarTitle(getString(R.string.menu_settings))
        initLanguageTVAdapter()
    }

    private fun onLanguageTVClick() {
        binding.autoCompleteTVLanguages.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedLanguage = adapterView.getItemAtPosition(position).toString()
            binding.autoCompleteTVLanguages.setText(selectedLanguage)
            val languageCode =
                if (selectedLanguage == getString(R.string.english)) Constants.ENGLISH_CODE else
                    Constants.ARABIC_CODE
            applyLanguageChange(languageCode)

        }
    }

    private fun applyLanguageChange(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        recreateActivity()


    }

    private fun initLanguageTVAdapter() {
        val languages = resources.getStringArray(R.array.languages).toList()
        val languagesAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        binding.autoCompleteTVLanguages.setAdapter(languagesAdapter)
    }


    private fun setInitialLanguageState() {
        val currentLocalCode =
            AppCompatDelegate.getApplicationLocales()[0]?.language ?: getCurrentDeviceLanguageCode(
                requireContext()
            )
        val language =
            if (currentLocalCode == Constants.ENGLISH_CODE) getString(R.string.english) else getString(
                R.string.arabic
            )
        binding.autoCompleteTVLanguages.setText(language)
    }




}