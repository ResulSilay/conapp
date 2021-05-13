package com.adapter.multiple.data.api.service

import com.adapter.multiple.data.api.ApiConst.STORE_CONTENT_SEARCH_URL
import com.adapter.multiple.data.api.response.StoreContentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreService {

    @GET(STORE_CONTENT_SEARCH_URL)
    suspend fun search(@Query("term") term: String? = ""): Response<StoreContentResponse>
}