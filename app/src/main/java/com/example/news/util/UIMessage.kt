package com.example.news.util

import androidx.annotation.StringRes

data class UIMessage(
    var isLoading: Boolean = false,
    var posAction: (() -> Unit)? = null,
    var errorMessage: String? = null,
    @StringRes var errorMessageId: Int? = null,
    var shouldDisplayNoArticlesFound: Boolean = false,
    var requestingNextPage:Boolean=false
)