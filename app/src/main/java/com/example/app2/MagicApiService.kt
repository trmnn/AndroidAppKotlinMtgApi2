package com.example.app2

import android.adservices.adid.AdId
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path

interface MagicApiService {
    @GET("/v1/cards")
    suspend fun getMagicCards(): Response<MagicCardsResponse>

    @GET("/v1/cards/{cardId}")
    suspend fun getCardDetails(@Path("cardId") cardId: String): Response<MagicCardDetails>

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
