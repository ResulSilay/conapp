package com.adapter.multiple.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adapter.multiple.data.api.repository.StoreRepository
import com.adapter.multiple.data.api.response.StoreContentResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivityViewModel(private val storeRepository: StoreRepository) : ViewModel() {

    var storeContentLiveData: MutableLiveData<StoreContentResponse> = MutableLiveData()

    fun getContent(searchText: String? = null) {

        CoroutineScope(Dispatchers.IO).launch {

            storeRepository.search(searchText = searchText).let {
                if (it.isSuccessful) {
                    it.body()?.let { storeContentResponse ->
                        storeContentLiveData.postValue(storeContentResponse)
                    }
                }
            }
        }
    }

}