package com.example.app2

data class MagicCardsResponse(
    val cards: List<MagicCard>
)

data class MagicCard(
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val subtypes: List<String>,
    val rarity: String,
    val text: String
)
