package com.asgar72.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<User>
}
