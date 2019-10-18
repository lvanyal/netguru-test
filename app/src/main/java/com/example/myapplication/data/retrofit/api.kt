package com.example.myapplication.data.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val api = createRetrofit().create(ApiService::class.java)

private fun createRetrofit(): Retrofit {
    return Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://sd2-hiring.herokuapp.com/")
        .build()
}