package com.testRetrofit.unsplash_app_tutorial.retrofit

import com.google.gson.JsonElement
import com.testRetrofit.unsplash_app_tutorial.utils.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    // https://www.unsplash.com/search/photos/?query=""

    @GET(API.SEARCH_PHOTOS)
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>

}
