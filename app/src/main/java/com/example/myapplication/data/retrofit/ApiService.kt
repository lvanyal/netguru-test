package com.example.myapplication.data.retrofit

import com.example.myapplication.data.response.UsersResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("/api/users?offset=10&limit=10")
    fun getUsers(): Single<UsersResponse>
}