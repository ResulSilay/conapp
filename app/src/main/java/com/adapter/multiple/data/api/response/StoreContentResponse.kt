package com.adapter.multiple.data.api.response

import com.adapter.multiple.data.api.model.ResultModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoreContentResponse(
    @SerializedName("resultCount")
    val resultCount: Int = 0,
    @SerializedName("results")
    val results: ArrayList<ResultModel> = arrayListOf()
) : Serializable