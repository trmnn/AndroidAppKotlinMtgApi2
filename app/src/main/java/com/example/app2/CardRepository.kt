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


class CardDetailRepository {
    suspend fun getCardDetails(cardId: String): MagicCardDetails {
        val response = MagicApiService.retrofitService.getCardDetails(cardId)
        return if (response.isSuccessful) {
            println("Successful response for card with ID: $cardId")
            println(response.body())
            response.body() ?: throw IllegalStateException("Empty response body")
        } else {
            println("WRONG response for card with ID: $cardId")
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            throw IllegalStateException("Failed to fetch card details $cardId: $errorMessage")
        }
    }
}

