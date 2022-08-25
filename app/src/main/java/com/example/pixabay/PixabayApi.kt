package com.example.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    fun getImage(@Query("key") key:String = "29413716-28f7165a7371530edbf07334d",
                 @Query("q") q:String,
                 @Query("page") page: Int,
                 @Query("per_page") perPage: Int = 10
    ): Call<PixabayModel>

}