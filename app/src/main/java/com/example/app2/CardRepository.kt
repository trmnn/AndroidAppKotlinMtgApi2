package com.example.app2

class CardRepository {
    suspend fun getMagicCards(): List<MagicCard> {
        val response = MagicApiService.retrofitService.getMagicCards()
        return if (response.isSuccessful) {
            response.body()?.cards ?: emptyList()
        } else {
            emptyList()
        }
    }
}
