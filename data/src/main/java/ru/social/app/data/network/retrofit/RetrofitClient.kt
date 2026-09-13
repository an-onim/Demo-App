package ru.social.app.data.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://www.dnd5eapi.co/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

object RpgApiClient {

    val service: RpgApi by lazy {
        RetrofitClient.retrofit.create(RpgApi::class.java)
    }

}