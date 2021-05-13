package com.adapter.multiple.data.api.repository

import com.adapter.multiple.data.api.ApiClient
import com.adapter.multiple.data.api.response.StoreContentResponse
import com.adapter.multiple.data.api.service.StoreService
import retrofit2.Response

class StoreRepository {

    private var networkManager = ApiClient.instance().client()

    suspend fun search(searchText: String? = ""): Response<StoreContentResponse> {
        return networkManager.create(StoreService::class.java).search(searchText)
    }
}