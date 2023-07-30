package com.febinrukfan.newsfeed.newsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.febinrukfan.newsfeed.newsfeed.model.repository.NewsFeedRepository

class NewsFeedViewModelProviderFactory(
    private val app: Application,
    private val newsFeedRepository: NewsFeedRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(app, newsFeedRepository) as T
    }
}