package com.example.myapplication.data.retrofit

import com.example.myapplication.data.response.UsersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/users?limit=10")
    fun getUsers(@Query("offset") offset: Int): Single<UsersResponse>
}