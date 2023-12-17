package com.example.app2

import retrofit2.http.GET
import retrofit2.Response

interface MagicApiService {
    @GET("/v1/cards")
    suspend fun getMagicCards(): Response<MagicCardsResponse>

    companion object {
        private const val BASE_URL = "https://api.magicthegathering.io"

        val retrofitService: MagicApiService by lazy {
            retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
                .create(MagicApiService::class.java)
        }
    }
}
