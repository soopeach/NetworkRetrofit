package com.soopeach.practiceForRetrofit.retrogit

import Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TestService {
    @GET("users/{id}/repos")
    fun users(@Path("id") pathId : String) :Call<Repository>
}
