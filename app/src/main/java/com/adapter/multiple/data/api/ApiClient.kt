package com.adapter.multiple.data.api

import com.adapter.multiple.data.api.ApiConst.BASE_HOST
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null
        private var instance: ApiClient? = null

        fun instance(): ApiClient {
            if (instance == null) {
                return ApiClient()
            }

            return instance as ApiClient
        }
    }

    fun client(): Retrofit {

        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS).build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit!!
    }
}