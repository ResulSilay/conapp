package com.adapter.multiple.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adapter.multiple.data.api.repository.StoreRepository

class SearchViewModelFactory(private val storeRepository: StoreRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SearchActivityViewModel::class.java))
            return SearchActivityViewModel(storeRepository = storeRepository) as T

        throw IllegalArgumentException("Unknown viewModel class.")
    }
}